package kr.co.leem.system.scripts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * Hierarchical structures with Java Enums - http://blog.cedarsoft.com/2010/10/hierarchical-structures-with-java-enums/
 *
 * @author Johannes Schneider
 * @author modified by 임 성천.
 */
public enum OsType {
	OS(null),
		Windows(OS),
			WindowsNT(Windows),
				WindowsNTWorkstation(WindowsNT),
				WindowsNTServer(WindowsNT),
				WindowsNTunknown(WindowsNT),	// JVM 버전 등이 맞지 않으면 Windows 7 등이 Windows NT (unknown) 과 같이 나오는 경우가 있음
			Windows2000(Windows),
				Windows2000Server(Windows2000),
				Windows2000Workstation(Windows2000),
			Windows2003(Windows),
			WindowsServer2008(Windows),
				WindowsServer2008R2(WindowsServer2008),
			WindowsXp(Windows),
			WindowsVista(Windows),
			Windows7(Windows),
			Windows95(Windows),
			Windows98(Windows),
			WindowsMe(Windows),
		Unix(OS) {
			@Override
			public boolean supportsXWindowSystem() {
				return true;
			}
			
			public boolean supportsPosix() {
				return true;
			}
		},
			SystemV(Unix),
				AIX(SystemV) {
					@Override
					public boolean supportsPosix() {
						return false;
					}
				},
				HpUx(SystemV) {
					@Override
					public boolean supportsPosix() {
						return false;
					}
				},
				IRIX(SystemV),
				SCOUnix(SystemV),
				Solaris(SystemV),
			BSD(Unix),
				SunOs(BSD),
				FreeBSD(BSD),
					MacOsX(FreeBSD),
			DigitalUnix(Unix),
				OSF1(DigitalUnix),
			MpeIx(Unix),
			Linux(Unix),
		UserSpecified(OS),
		Other(OS),
		;
	// @formatter:on
	
	private final OsType parent;
	
	private final List<OsType> children = new ArrayList<OsType>();
	
	private final List<OsType> allChildren = new ArrayList<OsType>();
	
	OsType(OsType parent) {
		this.parent = parent;
		if (parent != null) {
			parent.addChild(this);
		}
	}
	
	public boolean supportsPosix() {
		return false;
	}
	
	public OsType parent() {
		return parent;
	}
	
	/**
	 * 매개변수 값과 현재 운영체제 값이 동일한지 여부를 반환함. 
	 * 
	 * @param type 운영체제 값.
	 * @return 매개변수 값과 현재 운영체제 값이 동일한지 여부.
	 */
	public boolean is(OsType type) {
		if (type == null) {
			return false;
		}
		
		for (OsType osType = this; osType != null; osType = osType.parent()) {
			if (type == osType) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 자식 OS타입 값을 반환함.
	 * 
	 * @return 자식 운영체제 타입 값.
	 */
	public List<? extends OsType> children() {
		return Collections.unmodifiableList(children);
	}
	
	/**
	 * 자식 OS타입 값을 반환함.
	 * 
	 * @return 모든 자식 운영체제 타입 값.
	 */
	public List<? extends OsType> allChildren() {
		return Collections.unmodifiableList(allChildren);
	}
	
	/**
	 * 자식  OS타입 값 추가.
	 * @param child 운영체제 타입값.
	 */
	private void addChild(OsType child) {
		this.children.add(child);
		
		List<OsType> greatChildren = new ArrayList<OsType>();
		greatChildren.add(child);
		greatChildren.addAll(child.allChildren());
		
		OsType currentAncestor = this;
		while (currentAncestor != null) {
			currentAncestor.allChildren.addAll(greatChildren);
			currentAncestor = currentAncestor.parent;
		}
	}
	
	/**
	 * XWindow system 지원여부.
	 * @return 지원할 경우 true를 반환함.
	 */
	public boolean supportsXWindowSystem() {
		if (parent == null) {
			return false;
		}
		
		return parent.supportsXWindowSystem();
	}
}