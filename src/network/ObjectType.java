package network;

import java.io.Serializable;

public enum ObjectType implements Serializable {
	LOGIN, LOGIN_ANS, LOGOFF, NEW_TICKET, NEW_MESS, REFRESH_TREE, TREESET, OPEN_TICKET, LIST_MESS;
}