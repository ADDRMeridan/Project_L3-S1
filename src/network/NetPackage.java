package network;

import java.io.Serializable;

public class NetPackage implements Serializable {

	private static final long serialVersionUID = 1L;
	private ObjectType objType;
	private Object obj;
	
	public NetPackage(ObjectType objType, Object obj) {
		super();
		this.objType = objType;
		this.obj = obj;
	}
	
	public ObjectType getObjType() {
		return objType;
	}
	
	public Object getObj() {
		return obj;
	}	
	
}
