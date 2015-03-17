package kr.co.leem.system;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.leem.utils.lang.DateUtils;
import kr.co.leem.system.domains.SigarAccessor;
import org.hyperic.sigar.FileInfo;
import org.hyperic.sigar.FileWatcher;
import org.hyperic.sigar.FileWatcherThread;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

/**
 * FileMonitor.
 * 
 * @author 임 성천.
 * 
 */
public abstract class FileMonitor extends BaseSystemUtils {
	
	/**
	 * 로그 설정.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileMonitor.class);
	
	private static boolean running = false;
	
	/**
	 * 파일 경로 모니터링 시작.
	 * 
	 * @param dirPath 모니터링할 디렉토리 경로.
	 * @return 정상적으로 실행되었으면 true를 반환함.
	 * 
	 * @see org.hyperic.sigar.FileWatcherThread
	 */
	public static boolean startMonitor(String dirPath) {
		return startMonitor(dirPath, FileWatcherThread.DEFAULT_INTERVAL);
	}
	
	/**
	 * 파일 경로 모니터링 시작.
	 * 
	 * @param dirPath 모니터링할 디렉토리 경로.
	 * @param interval 모니터링 간격(300000 = 300 millisecond)
	 * @return true if the FileWatcherThread is started successfully, false if not
	 */
	public static boolean startMonitor(String dirPath, long interval) {
		return startMonitor(logger, dirPath, interval);
	}
	
	/**
	 * 모니터링 시작.
	 * 
	 * @param log logger
	 * @param dirPath 모니터링할 디렉로리 경로.
	 * @param interval 시간간격. (300000 = 300 millisecond)
	 * @return 정상적으로 시작된 경우 true.
	 */
	public static boolean startMonitor(Logger logger, String dirPath,
			long interval) {
		if (isRunning()) {
			logger.error("모니터가 이미 실행되었습니다.");
			return false;
		}
		
		forceReviveSingletonFileWatcherThread();
		
		synchronized (dirPath) {
			if (start(logger, FileWatcherThread.getInstance(), dirPath, interval)) {
				setRunning(true);
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * 모니터링 시작.
	 * 
	 * @param log logger
	 * @param dirPath 모니터링할 디렉로리 경로.
	 * @param interval 시간간격. (300000 = 300 millisecond)
	 * @return 정상적으로 시작된 경우 true.
	 */
	public static FileWatcherThread start(Logger logger, String dirPath, long interval) {
		FileWatcherThread watcherThread = new FileWatcherThread();
		start(logger, watcherThread, dirPath, interval);
		
		return watcherThread;
	}
	
	/**
	 * 모니터링 시작.
	 * 
	 * @param log logger
	 * @param watcherThread FileWatcherThread
	 * @param dirPath 모니터링할 디렉로리 경로.
	 * @param interval 시간간격. (300000 = 300 millisecond)
	 * @return 정상적으로 시작된 경우 true.
	 * @see org.hyperic.sigar.FileWatcherThread
	 * @see org.hyperic.sigar.FileWatcher
	 */
	protected static boolean start(final Logger logger,
			final FileWatcherThread watcherThread, final String dirPath,
			final long interval) {
		
		return processIO(new IOCallback<Boolean>() {
			private Map<File, Long> baseFiles = new HashMap<File, Long>();
			
			public Boolean doInProcessIO() throws IOException, SigarException {
				final File dirPathFile = new File(dirPath);
				if (!dirPathFile.exists() || !dirPathFile.canRead()) {
					logger.error(
							"경로를 읽을 수 없거나 존재하지 않습니다. : {}",
							dirPath);
					return false;
				}
				
				watcherThread.setInterval(interval);
				
				FileWatcher watcher = new FileWatcher(SigarAccessor.getInstance().getSigar()) {
					private List<String> toRemoveList = new ArrayList<String>();
					
					public void onChange(FileInfo info) {
						logger.info("MOD${}${}", new Object[] { info.getName(),
								info.diff() });
					}
					
					public void onNotFound(FileInfo info) {
						logger.info("DEL${}", info.getName());
						toRemoveList.add(info.getName());
					}
					
					public void onException(FileInfo info, SigarException e) {
						logger.error("Error checking {}:", info.getName(), e);
					}
					
					@Override
					public void check() {
						addWatcherCreatedFile();
						super.check();
						removeDeletedFiles();
					}
					private void removeDeletedFiles() {
						for (String deletedFile : toRemoveList) {
							remove(deletedFile);
						}
						toRemoveList.clear();
					}
					
					private void addWatcherCreatedFile() {
						for (File file : dirPathFile.listFiles()) {
							if (baseFiles.get(file) == null) {
								baseFiles.put(file, file.lastModified());
								try {
									logger.info(
											"NEW${}${Mtime: {}}{Size: {}}",
											new Object[] {
													file.getAbsolutePath(),
													DateUtils.date2String(
															new Date(
																	file.lastModified()),
															"MMM dd HH:mm"),
													file.length() });
									add(file);
								} catch (Exception e) {
									logger.error(
											"sigar FileWatcher add failed. {}",
											file.getAbsolutePath(), e);
								}
							}
						}
					}
					
				};
				watcher.setInterval(watcherThread.getInterval());
				
				if (dirPathFile.isDirectory()) {
					File[] orgFiles = dirPathFile.listFiles();
					// org baseFiles
					for (File file : orgFiles) {
						baseFiles.put(file, file.lastModified());
					}
					// if Korean file name exists for Windows, exception occurs
					// in JNI!
					watcher.add(orgFiles);
				}
				
				logger.info("FileWatcherThread started.");
				watcherThread.add(watcher);
				watcherThread.doStart();
				
				return true;
			}
		});
	}
	
	/**
	 * 모니터링 정지.
	 */
	public static void stopMonitor() {
		stopMonitor(logger);
	}
	
	/**
	 * 모니터링 정지.
	 * 
	 * @param log logger
	 */
	public static void stopMonitor(Logger log) {
		FileWatcherThread watcherThread = FileWatcherThread.getInstance();
		watcherThread.doStop();
		setRunning(false);
		logger.info("FileWatcherThread stopped.");
	}
	
	/**
	 *모니터링 정지.
	 * 
	 * @param log logger.
	 * @param watcherThread FileWatcherThread.
	 */
	public static void stop(Logger logger, FileWatcherThread watcherThread) {
		watcherThread.doStop();
		logger.info("FileWatcherThread stopped.");
	}
	
	private static void setRunning(boolean running) {
		FileMonitor.running = running;
	}
	
	/**
	 * 모니터링 중인지 여부를 반환함.
	 * 
	 * @return 모니터링 중인 경우 true.
	 */
	public static boolean isRunning() {
		return running;
	}
	
	public static void forceReviveSingletonFileWatcherThread() {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws Exception {
				Field field = ReflectionUtils.findField(
						FileWatcherThread.class, "shouldDie");
				ReflectionUtils.makeAccessible(field);
				field.setBoolean(FileWatcherThread.getInstance(), false);
				return null;
			}
		});
	}
}