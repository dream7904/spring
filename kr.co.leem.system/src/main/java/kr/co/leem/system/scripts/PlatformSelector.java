package kr.co.leem.system.scripts;

/**
 * PlatformSelector.
 * 
 * @author 임 성천.
 */
public class PlatformSelector {
	
	public static ScriptExecutor getScriptExecutor() {
		return getScriptExecutor(DefaultScriptExecutor.getOsType());
	}
	
	public static ScriptExecutor getScriptExecutor(OsType osType) {
		ScriptExecutor scriptExecutor = null;
		switch (osType) {
			case Windows:
			case Windows95:
			case Windows98:
			case WindowsMe:
			case WindowsNT:
			case WindowsNTServer:
			case WindowsNTWorkstation:
			case Windows2000:
			case Windows2000Server:
			case Windows2000Workstation:
			case WindowsXp:
			case WindowsVista:
			case Windows2003:
			case Windows7:
				scriptExecutor = WindowsScriptExecutor.getInstance();
				break;
			case Linux:
				scriptExecutor = LinuxScriptExecutor.getInstance();
				break;
			case MacOsX:
				scriptExecutor = MacOsXScriptExecutor.getInstance();
				break;
			default:
				scriptExecutor = DefaultScriptExecutor.getInstance();
				break;
		}
		
		return scriptExecutor;
	}
}