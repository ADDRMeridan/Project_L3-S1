package network;

import java.io.Serializable;

public enum ObjectType implements Serializable {
	LOGIN, LOGIN_ANS, LOGOFF, NEW_TICKET, NEW_MESSAGE, REFRESH_TREE, TREESET;
}
