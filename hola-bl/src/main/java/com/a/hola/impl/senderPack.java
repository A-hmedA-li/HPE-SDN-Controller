package com.a.hola.impl;

import com.hp.sdn.model.ConnectionPoint;
import com.hp.util.ip.BigPortNumber;

public class senderPack extends pair {
public ConnectionPoint c ; 
public BigPortNumber outport ; 

senderPack(ConnectionPoint conection , BigPortNumber out){

	c = conection ; 
	outport = out ; 
	
}
}
