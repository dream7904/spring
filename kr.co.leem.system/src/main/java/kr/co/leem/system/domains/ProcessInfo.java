package kr.co.leem.system.domains;

import java.io.Serializable;

/**
 * ProcessInfo.
 * 
 * @author 임 성천.
 */
public class ProcessInfo implements Serializable {
	
	private static final long serialVersionUID = 12347612312167378L;
	
	private ProcessCpuInfo processCpuInfo;
	
	private ProcessExeInfo processExeInfo;
	
	private ProcessStateInfo processStateInfo;
	
	private ProcessMemoryInfo processMemoryInfo;
	
	public ProcessCpuInfo getProcessCpuInfo() {
		return processCpuInfo;
	}
	
	public void setProcessCpuInfo(ProcessCpuInfo processCpuInfo) {
		this.processCpuInfo = processCpuInfo;
	}
	
	public ProcessExeInfo getProcessExeInfo() {
		return processExeInfo;
	}
	
	public void setProcessExeInfo(ProcessExeInfo processExeInfo) {
		this.processExeInfo = processExeInfo;
	}
	
	public ProcessStateInfo getProcessStateInfo() {
		return processStateInfo;
	}
	
	public void setProcessStateInfo(ProcessStateInfo processStateInfo) {
		this.processStateInfo = processStateInfo;
	}
	
	public ProcessMemoryInfo getProcessMemoryInfo() {
		return processMemoryInfo;
	}
	
	public void setProcessMemoryInfo(ProcessMemoryInfo processMemoryInfo) {
		this.processMemoryInfo = processMemoryInfo;
	}
}