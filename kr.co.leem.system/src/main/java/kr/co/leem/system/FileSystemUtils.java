/*
 * Copyright 2008-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kr.co.leem.system;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import kr.co.leem.utils.lang.DateUtils;
import kr.co.leem.system.domains.FileInfo;
import kr.co.leem.system.domains.SigarAccessor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.hyperic.sigar.SigarException;

/**
 * FileSystemUtils.
 *
 * @author 임 성천.
 *
 */
public class FileSystemUtils extends BaseSystemUtils {
	
	/**
	 * 파일/디렉토리 여부.
	 */
	public enum isFileOrDir {
		IS_FILE, IS_DIRECTORY
	}
	
	/**
	 * 파일명 검색 종류.
	 */
	public enum searchFileName {
		FULL_MATCH, PREFIX, SUFFIX
	}
	
	/**
	 * 파일 또는 디렉토리가 읽기 가능한지 여부를 반환함.
	 *
	 * @param path 파일 또는 디렉토리 경로명.
	 * @return 읽기 가능일 경우 true.
	 * @see File#canRead()
	 */
	public static boolean canRead(String path) {
		boolean check = false;
		try {
			File checkFile = new File(path);
			if (checkFile.exists()) {
				check = checkFile.canRead();
			}
		} catch (SecurityException e) {
			check = false;
		}
		return check;
	}
	
	/**
	 * 파일 또는 디렉토리가 쓰기 가능한지 여부를 반환함.
	 *
	 * @param path 파일 또는 디렉토리 경로명.
	 * @return 읽기 가능일 경우 true.
	 * @see File#canWrite()
	 */
	public static boolean canWrite(String path) {
		boolean check = false;
		try {
			File checkFile = new File(path);
			if (checkFile.exists()) {
				check = checkFile.canWrite();
			}
		}
		catch (SecurityException e) {
			check = false;
		}
		return check;
	}
	
	/**
	 * 디렉토리를 복사함.
	 *
	 * @param srcDir 원본 디렉토리 경로명.
	 * @param destDir 복사할 디렉토리 경로명.
	 * @see FileUtils#copyDirectory(File, File)
	 */
	public static void copyDirectory(final String srcDir, final String destDir) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				FileUtils.copyDirectory(new File(srcDir), new File(destDir));
				return null;
			}
		});
	}
	
	/**
	 * 디렉토리를 복사함.
	 *
	 * @param srcDir 원본 디렉토리 경로명.
	 * @param destDir 복사할 디렉토리 경로명.
	 * @param preserveFileDate 날짜유지 여부.
	 * @see FileUtils#copyDirectory(File, File, boolean)
	 */
	public static void copyDirectory(final String srcDir, final String destDir, final boolean preserveFileDate) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				FileUtils.copyDirectory(new File(srcDir), new File(destDir), preserveFileDate);
				return null;
			}
		});
	}
	
	/**
	 * 디렉토리를 복사함.
	 *
	 * @param srcDir 원본 디렉토리 경로명.
	 * @param destDir 복사할 디렉토리 경로명.
	 * @param isFileOrDir IS_FILE : 파일 복사, IS_DIRECTORY : 디렉토리 복사.
	 */
	public static void copyDirectory(final String srcDir, final String destDir, final isFileOrDir isFileOrDir) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				copyDirectory(srcDir, destDir, isFileOrDir, true);
				return null;
			}
		});
	}
	
	/**
	 * 디렉토리를 복사함.
	 *
	 * @param srcDir 원본 디렉토리 경로명.
	 * @param destDir 복사할 디렉토리 경로명.
	 * @param isFileOrDir IS_FILE : 파일 복사, IS_DIRECTORY : 디렉토리 복사.
	 * @param preserveFileDate 날짜유지 여부.
	 * @see FileUtils#copyDirectory(File, File, FileFilter, boolean)
	 * @see FileFileFilter#FILE
	 * @see DirectoryFileFilter#DIRECTORY
	 */
	public static void copyDirectory(final String srcDir, final String destDir, final isFileOrDir filedir,
			final boolean preserveFileDate) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				switch (filedir) {
				case IS_FILE:
					FileUtils.copyDirectory(new File(srcDir), new File(destDir), FileFileFilter.FILE, preserveFileDate);
					break;
				case IS_DIRECTORY:
					FileUtils.copyDirectory(new File(srcDir), new File(destDir), DirectoryFileFilter.DIRECTORY,
							preserveFileDate);
					break;
				default:
					FileUtils.copyDirectory(new File(srcDir), new File(destDir), DirectoryFileFilter.DIRECTORY,
							preserveFileDate);
				}
				return null;
			}
		});
	}
	
	/**
	 * 디렉토리를 복사함.
	 *
	 * @param srcDir 원본 디렉토리 경로명.
	 * @param destDir 복사할 디렉토리 경로명.
	 * @param extension 확장자명.
	 */
	public static void copyDirectory(final String srcDir, final String destDir, final String extension) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				copyDirectory(srcDir, destDir, extension, true);
				return null;
			}
		});
	}
	
	/**
	 * 디렉토리를 복사함.
	 *
	 * @param srcDir 원본 디렉토리 경로명.
	 * @param destDir 복사할 디렉토리 경로명.
	 * @param extension 확장자명.
	 * @param preserveFileDate 날짜유지 여부.
	 * @see FileUtils#copyDirectory(File, File, FileFilter, boolean)
	 * @see FileFilterUtils#suffixFileFilter(String)
	 * @see FileFilterUtils#or(IOFileFilter, IOFileFilter)
	 */
	public static void copyDirectory(final String srcDir, final String destDir, final String extension,
			final boolean preserveFileDate) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				IOFileFilter suffixFilter = FileFilterUtils.suffixFileFilter(extension);
				FileFilter filter = FileFilterUtils.or(DirectoryFileFilter.DIRECTORY, suffixFilter);
				FileUtils.copyDirectory(new File(srcDir), new File(destDir), filter, preserveFileDate);
				return null;
			}
		});
	}
	
	/**
	 * 디렉토리를 복사함.
	 *
	 * @param srcDir 원본 디렉토리 경로명.
	 * @param destDir 복사할 디렉토리 경로명.
	 * @param date 날짜.(yyyy-MM-dd)
	 * @param acceptOlder 오래된 파일 포함여부.
	 */
	public static void copyDirectory(final String srcDir, final String destDir, final Date cutoffDate,
			final boolean acceptOlder) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				copyDirectory(srcDir, destDir, cutoffDate, acceptOlder, true);
				return null;
			}
		});
	}
	
	/**
	 * 디렉토리를 복사함.
	 *
	 * @param srcDir 원본 디렉토리 경로명.
	 * @param destDir 복사할 디렉토리 경로명.
	 * @param date 날짜.(yyyy-MM-dd)
	 * @param acceptOlder 오래된 파일 포함여부.
	 * @param preserveFileDate 날짜 유지 여부.
	 * @see FileUtils#copyDirectory(File, File, FileFilter, boolean)
	 * @see FileFilterUtils#ageFileFilter(Date)
	 * @see FileFilterUtils#or(IOFileFilter, IOFileFilter)
	 */
	public static void copyDirectory(final String srcDir, final String destDir, final Date cutoffDate,
			final boolean acceptOlder, final boolean preserveFileDate) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				IOFileFilter ageFilter = FileFilterUtils.ageFileFilter(cutoffDate, acceptOlder);
				FileFilter filter = FileFilterUtils.or(DirectoryFileFilter.DIRECTORY, ageFilter);
				FileUtils.copyDirectory(new File(srcDir), new File(destDir), filter, preserveFileDate);
				return null;
			}
		});
	}
	
	/**
	 * 디렉토리를 복사함.
	 *
	 * @param srcDir 원본 디렉토리 경로명.
	 * @param destDir 복사할 디렉토리 경로명.
	 * @param fileSize 파일 크기
	 * @param acceptLarger 파일 사이즈 크기 보다 큰 파일 허용여부.
	 */
	public static void copyDirectory(final String srcDir, final String destDir, final long fileSize,
			final boolean acceptLarger) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException, IllegalArgumentException {
				copyDirectory(srcDir, destDir, fileSize, acceptLarger, true);
				return null;
			}
		});
	}
	
	/**
	 * 디렉토리를 복사함.
	 *
	 * @param srcDir 원본 디렉토리 경로명.
	 * @param destDir 복사할 디렉토리 경로명.
	 * @param fileSize 파일 크기
	 * @param acceptLarger 파일 사이즈 크기 보다 큰 파일 허용여부.
	 * @param preserveFileDate 날짜 유지 여부.
	 * @see FileUtils#copyDirectory(File, File, FileFilter, boolean)
	 * @see FileFilterUtils#sizeFileFilter(long, boolean)
	 * @see FileFilterUtils#or(IOFileFilter, IOFileFilter)
	 */
	public static void copyDirectory(final String srcDir, final String destDir, final long fileSize,
			final boolean acceptLarger, final boolean preserveFileDate) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException, IllegalArgumentException {
				IOFileFilter sizeFilter = FileFilterUtils.sizeFileFilter(fileSize, acceptLarger);
				IOFileFilter sizeFiles = FileFilterUtils.or(DirectoryFileFilter.DIRECTORY, sizeFilter);
				FileUtils.copyDirectory(new File(srcDir), new File(destDir), sizeFiles, preserveFileDate);
				return null;
			}
		});
	}
	
	/**
	 * 디렉토리를 복사함.
	 *
	 * @param srcDir 원본 디렉토리 경로명.
	 * @param destDir 복사할 디렉토리 경로명.
	 * @param minSize 파일 최소 크기.
	 * @param maxSize 파일 최대 크기.
	 */
	public static void copyDirectory(final String srcDir, final String destDir, final long minSize, final long maxSize) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException, IllegalArgumentException {
				copyDirectory(srcDir, destDir, minSize, maxSize, true);
				return null;
			}
		});
	}
	
	/**
	 * 디렉토리를 복사함.
	 *
	 * @param srcDir 원본 디렉토리 경로명.
	 * @param destDir 복사할 디렉토리 경로명.
	 * @param minSize 파일 최소 크기.
	 * @param maxSize 파일 최대 크기.
	 * @param preserveFileDate 날짜 유지 여부.
	 * 
	 * @see FileUtils#copyDirectory(File, File, FileFilter, boolean)
	 * @see FileFilterUtils#sizeRangeFileFilter(long, long)
	 * @see FileFilterUtils#or(IOFileFilter, IOFileFilter)
	 */
	public static void copyDirectory(final String srcDir, final String destDir, final long minSize, final long maxSize,
			final boolean preserveFileDate) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException, IllegalArgumentException {
				IOFileFilter sizeFileFilter = FileFilterUtils.sizeRangeFileFilter(minSize, maxSize);
				IOFileFilter sizeFiles = FileFilterUtils.or(DirectoryFileFilter.DIRECTORY, sizeFileFilter);
				FileUtils.copyDirectory(new File(srcDir), new File(destDir), sizeFiles, preserveFileDate);
				return null;
			}
		});
	}
	
	/**
	 * 디렉토리를 복사함.
	 *
	 * @param srcDir 원본 디렉토리 경로명.
	 * @param destDir 복사할 디렉토리 경로명.
	 * @param name 검색어.
	 * @param fileNameSearch 검색종류.
	 * prefix or suffix
	 */
	public static void copyDirectory(final String srcDir, final String destDir, final String name,
			final searchFileName searchFileName) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException, IllegalArgumentException {
				copyDirectory(srcDir, destDir, name, searchFileName, true);
				return null;
			}
		});
	}
	
	/**
	 * 디렉토리를 복사함.
	 *
	 * @param srcDir 원본 디렉토리 경로명.
	 * @param destDir 복사할 디렉토리 경로명.
	 * @param name 검색어.
	 * @param fileNameSearch 검색종류.
	 * @param preserveFileDate 날짜 유지 여부.
	 * @see FileUtils#copyDirectory(File, File, FileFilter, boolean)
	 * @see FileFilterUtils#nameFileFilter(long, long)
	 * @see FileFilterUtils#prefixFileFilter(String)
	 * @see FileFilterUtils#suffixFileFilter(String)
	 * @see FileFilterUtils#or(IOFileFilter, IOFileFilter)
	 */
	public static void copyDirectory(final String srcDir, final String destDir, final String name,
			final searchFileName fileNameSearch, final boolean preserveFileDate) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException, IllegalArgumentException {
				IOFileFilter fileFilter;
				switch (fileNameSearch) {
					case FULL_MATCH:
						fileFilter = FileFilterUtils.nameFileFilter(name);
						break;
					case PREFIX:
						fileFilter = FileFilterUtils.prefixFileFilter(name);
						break;
					case SUFFIX:
						fileFilter = FileFilterUtils.suffixFileFilter(name);
						break;
					default:
						fileFilter = FileFilterUtils.nameFileFilter(name);
						break;
				}
				
				IOFileFilter fileNameFiles = FileFilterUtils.or(DirectoryFileFilter.DIRECTORY, fileFilter);
				FileUtils.copyDirectory(new File(srcDir), new File(destDir), fileNameFiles, preserveFileDate);
				return null;
			}
		});
	}
	
	/**
	 * 디렉토리를 복사함.
	 *
	 * @param srcDir 원본 디렉토리 경로명.
	 * @param destDir 복사할 디렉토리 경로명.
	 * @see FileUtils#copyDirectoryToDirectory(File, File)
	 */
	public static void copyDirectoryToDirectory(final String srcDir, final String destDir) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				FileUtils.copyDirectoryToDirectory(new File(srcDir), new File(destDir));
				return null;
			}
		});
	}
	
	/**
	 * 디렉토리를 이동.
	 *
	 * @param srcDir 원본 디렉토리 경로명.
	 * @param destDir 이동시킬 디렉토리 경로명.
	 * @see FileUtils#moveDirectory(File, File)
	 */
	public static void moveDirectory(final String srcDir, final String destDir) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				FileUtils.moveDirectory(new File(srcDir), new File(destDir));
				return null;
			}
		});
	}

	/**
	 * 디렉토리를 이동.
	 *
	 * @param srcDir 원본 디렉토리 경로명.
	 * @param destDir 이동시킬 디렉토리 경로명.
	 * @param createDestDir 디렉토리 생성 여부.
	 * @see FileUtils#moveDirectoryToDirectory(File, File, boolean)
	 */
	public static void moveDirectoryToDirectory(final String src, final String destDir, final boolean createDestDir) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				FileUtils.moveDirectoryToDirectory(new File(src), new File(destDir), createDestDir);
				return null;
			}
		});
	}
	
	/**
	 * 디렉토리 생성.
	 *
	 * @param dirPath 생성할 디렉토리 경로.
	 * @see FileUtils#forceMkdir(File)
	 */
	public static void makeDirectory(final String dirPath) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				FileUtils.forceMkdir(new File(dirPath));
				return null;
			}
		});
	}
	
	/**
	 * 파일 / 디렉토리 삭제.
	 *
	 * @param path 파일 / 디렉토리 경로.
	 * @see FileUtils#forceDelete(File)
	 */
	public static void deleteFileDirectory(final String path) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				FileUtils.forceDelete(new File(path));
				return null;
			}
		});
	}
	
	/**
	 * 파일 복사.
	 *
	 * @param srcFile 원본 파일 경로명.
	 * @param destFile 복사할 파일 경로명.
	 * @see FileUtils#copyFile(File, File)
	 */
	public static void copyFile(final String srcFile, final String destFile) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				FileUtils.copyFile(new File(srcFile), new File(destFile));
				return null;
			}
		});
	}
	
	/**
	 * 파일 복사.
	 *
	 * @param srcFile 원본 파일 경로명.
	 * @param destFile 복사할 파일 경로명.
	 * @param preserveFileDate 날짜 유지 여부.
	 * @see FileUtils#copyFile(File, File, boolean)
	 */
	public static void copyFile(final String srcFile, final String destFile, final boolean preserveFileDate) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				FileUtils.copyFile(new File(srcFile), new File(destFile), preserveFileDate);
				return null;
			}
		});
	}
	
	/**
	 * 파일 복사.
	 *
	 * @param srcFile 원본 파일 경로명.
	 * @param destFile 복사할 파일 경로명.
	 * @see FileUtils#copyFileToDirectory(File, File)
	 */
	public static void copyFileToDirectory(final String srcFile, final String destDir) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				FileUtils.copyFileToDirectory(new File(srcFile), new File(destDir));
				return null;
			}
		});
	}
	
	/**
	 * 파일 복사.
	 * 
	 * @param srcFile 원본 파일 경로명.
	 * @param destFile 복사할 파일 경로명.
	 * @param preserveFileDate 날짜 유지 여부.
	 * @see FileUtils#copyFileToDirectory(File, File, boolean)
	 */
	public static void copyFileToDirectory(final String srcFile, final String destDir, final boolean preserveFileDate) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				FileUtils.copyFileToDirectory(new File(srcFile), new File(destDir), preserveFileDate);
				return null;
			}
		});
	}
	
	/**
	 * 파일 이동.
	 *
	 * @param srcFile 원본 파일 경로명.
	 * @param destFile 이동할 파일 경로명.
	 * @see FileUtils#moveFile(File, File)
	 */
	public static void moveFile(final String srcFile, final String destFile) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				FileUtils.moveFile(new File(srcFile), new File(destFile));
				return null;
			}
		});
	}
	
	/**
	 * 파일 이동.
	 *
	 * @param srcFile 원본 파일 경로명.
	 * @param destFile 이동할 파일 경로명.
	 * @param createDestDir 디렉토리 생성 여부.
	 * @see FileUtils#moveFileToDirectory(File, File, boolean)
	 */
	public static void moveFileToDirectory(final String srcFile, final String destDir, final boolean createDestDir) {
		processIO(new IOCallback<Object>() {
			public Object doInProcessIO() throws IOException, NullPointerException {
				FileUtils.moveFileToDirectory(new File(srcFile), new File(destDir), createDestDir);
				return null;
			}
		});
	}
	
	/**
	 * 파일 정보를 반환함.
	 *
	 * @param filePath file path or directory full path
	 * @return FileInfo file information
	 */
	public static FileInfo getInformation(final String filePath) {
		return processIO(new IOCallback<FileInfo>() {
			public FileInfo doInProcessIO() throws IOException, SigarException {
				return SigarAccessor.getFileInfo(filePath);
			}
		});
	}
	
	/**
	 * 파일 목록을 반환함.
	 *
	 * @param dirPath 디렉토리 경로명.
	 * @param extensions 확장자.
	 * @param recursive 서브 디렉토리 검색 여부.
	 * @return 파일 목록.
	 * @see FileUtils#listFiles(File, String[], boolean)
	 */
	public static File[] getFileList(final String dirPath, final String[] extensions, final boolean recursive) {
		return processIO(new IOCallback<File[]>() {
			public File[] doInProcessIO() throws IOException, NullPointerException {
				Collection<File> files;
				if (extensions[0].equals("all")) {
					files = FileUtils.listFiles(new File(dirPath), null, recursive);
				} else {
					files = FileUtils.listFiles(new File(dirPath), extensions, recursive);
				}
				return FileUtils.convertFileCollectionToFileArray(files);
			}
		});
	}
	
	/**
	 * 파일 목록을 반환함.
	 *
	 * @param dirPath 디렉토리 경로명.
	 * @param date based 날짜. (yyyy-MM-dd)
	 * @param acceptOlder 오래된 파일 검색 여부.
	 * @return 파일 목록.
	 * @see FileUtils#listFiles(File, IOFileFilter, IOFileFilter)
	 * @see FileFilterUtils#ageFileFilter(date, boolean)
	 */
	public static File[] getFileList(final String dirPath, final Date date, final boolean acceptOlder) {
		return processIO(new IOCallback<File[]>() {
			public File[] doInProcessIO() throws IOException, NullPointerException {
				IOFileFilter ageFileFilter = FileFilterUtils.ageFileFilter(date, acceptOlder);
				
				Collection<File> files = FileUtils.listFiles(new File(dirPath), ageFileFilter,
						DirectoryFileFilter.DIRECTORY);
				return FileUtils.convertFileCollectionToFileArray(files);
			}
		});
	}
	
	/**
	 * 파일 목록을 반환함.
	 *
	 * @param dirPath 디렉토리 경로명.
	 * @param size 파일 사이즈.
	 * @param acceptLarger 지정한 파일 사이즈 보다 큰 파일 포함여부.
	 * @return 파일 목록.
	 * @see FileUtils#listFiles(File, IOFileFilter, IOFileFilter)
	 * @see FileFilterUtils#sizeFileFilter(long, boolean)
	 */
	public static File[] getFileList(final String dirPath, final long size, final boolean acceptLarger) {
		return processIO(new IOCallback<File[]>() {
			public File[] doInProcessIO() throws IOException, NullPointerException {
				IOFileFilter sizeFileFilter = FileFilterUtils.sizeFileFilter(size, acceptLarger);
				
				Collection<File> files = FileUtils.listFiles(new File(dirPath), sizeFileFilter,
						DirectoryFileFilter.DIRECTORY);
				return FileUtils.convertFileCollectionToFileArray(files);
			}
		});
	}
	
	/**
	 * 파일 목록을 반환함.
	 *
	 * @param dirPath 디렉토리 경로명.
	 * @param minSize 파일 최소 크기.
	 * @param maxSize 파일 최대 크기.
	 * @return 파일 목록.
	 * @see FileUtils#listFiles(File, IOFileFilter, IOFileFilter)
	 * @see FileFilterUtils#sizeRangeFileFilter(long, long)
	 */
	public static File[] getFileList(final String dirPath, final long minSize, final long maxSize) {
		return processIO(new IOCallback<File[]>() {
			public File[] doInProcessIO() throws IOException, NullPointerException {
				IOFileFilter sizeFileFilter = FileFilterUtils.sizeRangeFileFilter(minSize, maxSize);
				
				Collection<File> files = FileUtils.listFiles(new File(dirPath), sizeFileFilter,
						DirectoryFileFilter.DIRECTORY);
				return FileUtils.convertFileCollectionToFileArray(files);
			}
		});
	}
	
	/**
	 * 파일 목록을 반환함.
	 *
	 * @param dirPath 디렉토리 경로명.
	 * @param name 검색어.
	 * @param fileNameSearch 검색 종류.
	 * @return 파일 목록.
	 * @see FileUtils#listFiles(File, IOFileFilter, IOFileFilter)
	 * @see FileFilterUtils#nameFileFilter(String)
	 * @see FileFilterUtils#prefixFileFilter(String)
	 * @see FileFilterUtils#suffixFileFilter(String)
	 */
	public static File[] getFileList(final String dirPath, final String name, final searchFileName fileNameSearch) {
		return processIO(new IOCallback<File[]>() {
			public File[] doInProcessIO() throws IOException, NullPointerException {
				IOFileFilter fileFilter;
				switch (fileNameSearch) {
					case FULL_MATCH:
						fileFilter = FileFilterUtils.nameFileFilter(name);
						break;
					case PREFIX:
						fileFilter = FileFilterUtils.prefixFileFilter(name);
						break;
					case SUFFIX:
						fileFilter = FileFilterUtils.suffixFileFilter(name);
						break;
					default:
						fileFilter = FileFilterUtils.nameFileFilter(name);
						break;
				}
				
				Collection<File> files = FileUtils.listFiles(new File(dirPath), fileFilter,
						DirectoryFileFilter.DIRECTORY);
				return FileUtils.convertFileCollectionToFileArray(files);
			}
		});
	}
	
	/**
	 * 파일 유무를 반환함.
	 *
	 * @param filepath 파일 경로.
	 * @return 존재하면 true.
	 * @see File#exists()
	 */
	public static boolean existsFile(final String filepath) {
		return processIO(new IOCallback<Boolean>() {
			public Boolean doInProcessIO() throws NullPointerException, SecurityException {
				File file = new File(filepath);
				if (file.exists() && file.isFile()) {
					return true;
				} else {
					return false;
				}
			}
		});
	}
	
	/**
	 * 디렉토리 유무를 반환함.
	 *
	 * @param dirPath 디렉토리 경로.
	 * @return 디렉토리가 존재하면 true.
	 * @see File#exists()
	 */
	public static boolean existsDir(final String dirPath) {
		return processIO(new IOCallback<Boolean>() {
			public Boolean doInProcessIO() throws NullPointerException, SecurityException {
				File dir = new File(dirPath);
				if (dir.exists() && dir.isDirectory()) {
					return true;
				} else {
					return false;
				}
			}
		});
	}
	
	/**
	 * 디렉토리 유무를 반환함.
	 * 
	 * @param dirPath 디렉토리 경로.
	 * @param fromDate 시작날짜. (yyyy-MM-dd)
	 * @param toDate 마지막 날짜. (yyyy-MM-dd)
	 * @return 파일이 존재하면 true.
	 * @see File#exists()
	 */
	public static boolean existsDir(final String dirPath, final String fromDate, final String toDate) {
		return processIO(new IOCallback<Boolean>() {
			public Boolean doInProcessIO() throws NullPointerException, SecurityException {
				File file = new File(dirPath);
				
				if (existsDir(dirPath)) {
					String lastModifed = DateUtils.date2String(new Date(file.lastModified()));
					if (DateUtils.string2Date(lastModifed).getTime() >= DateUtils.string2Date(fromDate).getTime()
							&& DateUtils.string2Date(lastModifed).getTime() <= DateUtils.string2Date(toDate).getTime()) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
		});
	}
}