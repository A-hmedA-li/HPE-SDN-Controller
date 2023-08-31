package com.a.hola.impl;



import com.hp.sdn.model.ConnectionPoint;
import com.hp.sdn.model.InterfaceId;


public class pair {
	public InterfaceId src ;
	public ConnectionPoint dst ; 
	public  int length ; 
	public int phero =50 ; 
	pair() {
		
	}
	pair(InterfaceId thing1 ,ConnectionPoint thing3 ,  int thing2){
		src = thing1 ; 
		dst = thing3 ;
		length = thing2 ; 
	}
	/*
	void stuffedData(){
		System.out.println("----------------------"
				+ "STUFFED DATA FUNCTION") ;
		Iterator <Node>it  = nd.getNodes() ; 
		System.out.println(it.hasNext()) ; 
		while (it.hasNext()){
			Node n = it.next() ;
			data.put(n.ip(),n.location());
		}
		for (Map.Entry<IpAddress, NodeLocation> m : data.entrySet()){
			System.out.println(m.getKey() + " " + m.getValue()) ; 
		}
		if (data.isEmpty())
			System.out.println("holyshitdata") ;
		System.out.println("----------------------"
				+ "END OF STUFFED DATA FUNCTION") ;
	}*/
}
