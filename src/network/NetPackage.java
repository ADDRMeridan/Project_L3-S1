package network;

public class NetPackage {

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
