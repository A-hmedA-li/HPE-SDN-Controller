package com.a.hola.impl;

import java.util.ArrayList;
import java.util.HashMap;

import com.hp.sdn.model.ConnectionPoint;
import com.hp.sdn.model.ElementId;

public class ComputMultiple {
	public HashMap <ElementId ,ArrayList<pair>> links = 
			new HashMap<>(); 
 
	
	public	HashMap<ElementId, Integer> vis = 
			new HashMap<ElementId, Integer>() ; 

	public ArrayList<Integer> cost = new ArrayList< Integer>(); 
	
	public ArrayList< ArrayList<senderPack>> mutipath = 
			new ArrayList< ArrayList<senderPack>>();
	public	int total_cost = 0 ; 
	public	int total_phero = 0 ;
	public ArrayList <Integer> phero = new ArrayList<Integer>() ; 
	public	ArrayList<Integer> cost_total= new ArrayList<Integer> () ;
	public	ArrayList<Integer> phero_total = new ArrayList<Integer>() ;
	
	public	ArrayList<ConnectionPoint> path = new ArrayList<ConnectionPoint>() ; 
	public	ComputMultiple( HashMap <ElementId ,ArrayList<pair>> t){
		links= t ; 
	}
		public void initVis () {
		for( ElementId cp : links.keySet()){
			vis.put(cp, 0) ;
		}
	}
	
		public ArrayList<senderPack> sender =  new ArrayList<senderPack>() ;
		public	void computepath(ConnectionPoint src 
				, ConnectionPoint dst ){
		
		
		vis.put(src.elementId(),1) ; 
		
		
	
		for (pair adj : links.get(src.elementId())){
			
			if (vis.get(adj.dst.elementId()) == 0){
				sender.add(new senderPack(src , adj.src.port()));
				
			 
				
				total_cost +=adj.length ; 
				
				cost.add(adj.length) ; 
				total_phero += adj.phero ; 
				
				phero.add(adj.phero) ; 
				 computepath(adj.dst , dst );
					
			}
		}

		
		if (src.elementId().equals(dst.elementId()) ){

			
			mutipath.add(new ArrayList<senderPack>()) ;
			for (senderPack cp : sender){
				mutipath.get(mutipath.size()-1).add(cp) ; 
			}
			mutipath.get(mutipath.size()-1).add(new senderPack(src , dst.interfaceId().port()));
			cost_total.add(total_cost) ; 
			phero_total.add(total_phero) ; 
		}
		
		
		if (!sender.isEmpty()){
			sender.remove(sender.size()-1) ;
		
			total_phero -=phero.remove(phero.size() - 1 );
			total_cost -= cost.remove(cost.size()-1);
			total_phero-=50 ; }
		
	
		vis.put(src.elementId() , 0) ;
		
	
	}
	
}

