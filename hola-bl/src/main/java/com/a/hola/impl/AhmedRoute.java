package com.a.hola.impl;

import static com.hp.of.lib.dt.DataPathId.dpid;

import java.util.ArrayList;

import com.hp.of.ctl.ControllerService;
import com.hp.of.ctl.prio.FlowClassAdapter;
import com.hp.of.lib.OpenflowException;
import com.hp.of.lib.dt.DataPathId;
import com.hp.of.lib.msg.OfmFlowMod;
import com.hp.util.ip.BigPortNumber;
import com.hp.util.ip.IpAddress;
import com.hp.util.ip.MacAddress;

public class AhmedRoute {
	IpAddress ip_src ; 
	IpAddress ip_dst;
	MacAddress mac_src ; 
	MacAddress mac_dst;
	ControllerService cs ; 
	AhmedRoute(IpAddress src , IpAddress dst , ControllerService css) {
		ip_src = src ; 
		ip_dst = dst ; 
		cs = css; 
		
	}
	AhmedRoute(MacAddress src , MacAddress dst , ControllerService css){
		mac_src = src ; 
		mac_dst = dst ; 
		cs = css; 
	}
	void dothings(ArrayList<ArrayList<senderPack>> multipath){
	ArrayList <senderPack> arr = multipath.get(0) ; 
	
	
	for (int i = 0 ; i< arr.size() ; i++){
		

		BigPortNumber outport =arr.get(i).outport ;
		BigPortNumber inport =arr.get(i).c.interfaceId().port(); 
		DataPathId dpid = dpid(arr.get(i).c.elementId().toString());
		

	
	packetout flow = new packetout(outport , inport , ip_dst , ip_src ) ; 	
	OfmFlowMod	 msg = flow.flowModCreathion() ; 
	
	try {
	cs.sendFlowMod(msg, dpid , new FlowClassAdapter() ) ;
	} catch (OpenflowException e) {
	
		System.out.println("err in sending the cs");
		System.out.println(e);
	} 
	
	}}
	void doArpThings(ArrayList<ArrayList<senderPack>> multipath){
	ArrayList <senderPack> arr = multipath.get(0) ; 
	
	
	for (int i = 0 ; i< arr.size() ; i++){
		

		BigPortNumber outport =arr.get(i).outport ;
		BigPortNumber inport =arr.get(i).c.interfaceId().port(); 
		DataPathId dpid = dpid(arr.get(i).c.elementId().toString());
		

	
	packetout flow = new Ahmedarp(outport , inport , mac_src , mac_dst ) ; 	
	OfmFlowMod	 msg = flow.flowModCreathion() ; 
	
	try {
	cs.sendFlowMod(msg, dpid , new FlowClassAdapter() ) ;
	} catch (OpenflowException e) {
	
		System.out.println("err in sending the cs");
		System.out.println(e);
	} 
	
	}}

	
}
