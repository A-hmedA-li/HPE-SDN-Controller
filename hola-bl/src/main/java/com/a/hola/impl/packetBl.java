package com.a.hola.impl;
import java.util.ArrayList;
import static com.hp.of.lib.dt.DataPathId.dpid;
 

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.felix.scr.annotations.Activate;

import com.hp.of.ctl.prio.FlowClassAdapter;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import java.util.Random ; 
import com.hp.of.ctl.ControllerService;
import com.hp.of.ctl.ErrorEvent;
import com.hp.of.ctl.pkt.MessageContext;
import com.hp.of.ctl.pkt.PacketListenerRole;
import com.hp.of.ctl.pkt.SequencedPacketListener;
import com.hp.of.lib.OpenflowException;
import com.hp.of.lib.dt.DataPathId;
import com.hp.of.lib.msg.OfmFlowMod;
import com.hp.sdn.model.ConnectionPoint;
import com.hp.sdn.model.ElementId;
import com.hp.sdn.model.Link;
import com.hp.sdn.model.Node;
import com.hp.sdn.node.NodeEvent;
import com.hp.sdn.node.NodeListener;
import com.hp.sdn.node.NodeService;
import com.hp.sdn.topo.TopologyEvent;
import com.hp.sdn.topo.TopologyListener;
import com.hp.sdn.topo.TopologyService;
import com.hp.util.ip.BigPortNumber;
import com.hp.util.ip.IpAddress;
import com.hp.util.ip.MacAddress;
import com.hp.util.pkt.Arp;
import com.hp.util.pkt.Icmp;
import com.hp.util.pkt.Ip;
import com.hp.util.pkt.Packet;
import com.hp.util.pkt.ProtocolId;
import com.hp.sdn.link.LinkService;
@Component  
public class packetBl {
	private HashMap <ElementId ,ArrayList<pair>> links = 
			new HashMap<>(); 
	

	private HashMap <IpAddress , ConnectionPoint> data =
			new HashMap<>();
	private HashMap<IpAddress, MacAddress> macs = new HashMap<IpAddress,MacAddress>() ; 
	
	private static  final int alt = 1238; 
	private  final obs obs  = new obs () ; 
		topo topo = new topo () ; 
	
	@Reference (cardinality = ReferenceCardinality.MANDATORY_UNARY ,  policy =  ReferencePolicy.STATIC)
	private volatile ControllerService cs ;
	@Reference (cardinality = ReferenceCardinality.MANDATORY_UNARY ,  policy =  ReferencePolicy.STATIC)
	private volatile NodeService nd ;
	@Reference (cardinality = ReferenceCardinality.MANDATORY_UNARY ,  policy =  ReferencePolicy.STATIC)
	private volatile LinkService ls ;
	@Reference (cardinality = ReferenceCardinality.MANDATORY_UNARY ,  policy =  ReferencePolicy.STATIC)
	private volatile TopologyService ts ;
;
	
	ndlisten cola = new ndlisten () ; 
	@Activate 
	void activate() {
		
			 
		cs.addPacketListener(obs , PacketListenerRole.DIRECTOR 
				, alt) ; 
		
		
		ts.addListener(topo);
		nd.addListener(cola);
	
	}
	@Deactivate
	void deactivate () { 

		cs.removePacketListener(obs);
		ts.removeListener(topo);
	for (int i = 0 ; i < 20 ; i ++){
		System.out.println("==================================================="
				+ "=========") ; 
		
		
	}
	}

	class topo implements TopologyListener {

		@Override
		public void event(TopologyEvent event) {
			for (int i = 0 ; i < 20 ; i ++){
				System.out.println("==================================================="
						+ "=========") ; 
				
				
			}
			
			if (System.currentTimeMillis() >=
					event.subject().activeAt()) {
				System.out.println(ts.getTopology().activeAt());
				System.out.println(System.currentTimeMillis());
				data.clear(); 
				links.clear(); 
				macs.clear();
				stuffedMap() ; 
		 
				
			}
		 
			
		}
		
	}
	class ndlisten implements NodeListener {

		@Override
		public void event(NodeEvent event) {
			Node node = event.subject();
			ConnectionPoint cp = node.location();
			
			data.put(node.ip(), cp);
			macs.put(node.ip(), node.mac()) ; 
			
			
		}
		
	}
	class obs implements SequencedPacketListener {
		
		ComputMultiple algo = new ComputMultiple (links) ; 
		
		@Override
		public void errorEvent(ErrorEvent err) {
			System.out.println(err) ; 
			
		}

		@Override
		public void event(MessageContext event) {
			
			
		Packet  p = event.decodedPacket();
		System.out.println("PACKET IN EVENT------");
		if (p.has(ProtocolId.ICMP)){
			System.out.println("HAS ICMP" ) ; 
			Icmp icmp = p.get(ProtocolId.ICMP) ; 
				System.out.println(icmp.id());
		}
	
		if (p.has(ProtocolId.IP)){
			System.out.println("HAS IP") ; 
			Ip ip = p.get(ProtocolId.IP) ; 
			
		 
			
				
				
				
				
				
				
				ConnectionPoint senderDpid =(ConnectionPoint)
						data.get(ip.srcAddr()) ; 
				ConnectionPoint targetDpid = (ConnectionPoint) 
						data.get(ip.dstAddr());
				System.out.println(data) ; 
			if (senderDpid != null && targetDpid != null){
			 ComputMultiple algo =  new ComputMultiple(links) ; 
			algo.initVis();
			algo.computepath(senderDpid, targetDpid);
			
		
		
			System.out.println("COST_TOTAL=============") ; 
			System.out.println(algo.cost_total) ;
			System.out.println("PHERO TOTAL ============") ; 
			System.out.println(algo.phero_total); 
			AhmedRoute ar = new AhmedRoute (ip.srcAddr() , ip.dstAddr() ,cs) ; 
			ar.dothings(algo.mutipath);
	
		}
			
			}
			
		
		
		if (p.has(ProtocolId.ARP)){
		//	if (false){
			Arp arp =p.get(ProtocolId.ARP);
			 
			
			MacAddress sender = arp.senderMacAddr();
			MacAddress target =	arp.targetMacAddr();
			System.out.println(sender + " " + target ) ; 
			System.out.println(macs) ; 
			ConnectionPoint senderDpid =(ConnectionPoint)
						data.get(arp.senderIpAddr()) ; 
				ConnectionPoint targetDpid = (ConnectionPoint) 
						data.get(arp.targetIpAddr());
			if (senderDpid != null && targetDpid != null){
				
				 ComputMultiple algo =  new ComputMultiple(links) ; 
					algo.initVis();
					algo.computepath(senderDpid, targetDpid);
					AhmedRoute ar = new AhmedRoute (arp.senderMacAddr() , arp.targetMacAddr() , cs) ; 
					ar.doArpThings(algo.mutipath);
			}
			else{
 			Ahmedarp send = new Ahmedarp(sender , target) ; 
 			OfmFlowMod  msg = send.flowModCreathion() ; 
			
				
			try {
				cs.sendFlowMod(msg, event.srcEvent().dpid() , new FlowClassAdapter());
			} catch (OpenflowException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}

			
		}
		
		}
		
	}
	


	
	
	void stuffedMap(){
		System.out.println("----------------------"
				+ "STUFFED MAP FUNCTION") ; 
		Random random = new Random () ; 
		Iterator<Link> l = ls.getLinks(); 
		
			
			
			while (l.hasNext()){
				boolean check = true  ;
				Link link = l.next() ;
		
	
			System.out.println(link.src().elementId()
						+ " " + link.dst().elementId()) ; 
			
			ElementId ad =  link.src().elementId() ; 
			if (!links.containsKey(ad)){
				links.put(ad, new ArrayList<pair> ());
			}
			int rand =  random.nextInt(100);
			pair dst = new pair(link.src().interfaceId() , link.dst() ,rand );
			for (pair cp : links.get(ad)){
				System.out.println(cp.equals(dst));
				if (cp.src.equals(dst.src) && cp.dst.equals(dst.dst) )
					check = false;
			}
			if (check){
				links.get(ad).add(dst) ;
				
			}
				check = true  ;
		
			
				ad = link.dst().elementId() ; 
				if (!links.containsKey(ad)){
					links.put(ad, new ArrayList<pair> ());
				}
				 rand =  random.nextInt(100);
					 dst = new pair(link.dst().interfaceId()
							, link.src() ,rand );
					 for (pair cp : links.get(ad)){
						 if (cp.src.equals(dst.src) && cp.dst.equals(dst.dst) )
							 check = false;
					 }
					 if (check)
					 links.get(ad).add(dst) ; 
					 System.out.println(check);
					 	check = true ; 
				 
		}
			System.out.println("----SIZE----") ;
			System.out.println(links.size()) ; 
		
		for (Map.Entry<ElementId, ArrayList<pair> > arr : links.entrySet()){
			System.out.println("---KEY---") ; 
			System.out.println(arr.getKey()) ; 
			
			System.out.println("---KEY SIZE---") ; 
			System.out.println(arr.getValue().size()) ; 
			for (pair sa : arr.getValue()){
				System.out.print(sa.dst);
				System.out.println(sa.src);
				System.out.print(" ");
				
			}
			System.out.println("\n");
			
		}
		if (links.isEmpty()) 
		System.out.println("holyshit") ; 
		System.out.println("----------------------"
				+ "EMD OF STUFFED MAP FUNCTION") ; 
	}
	/*
	HashMap<ConnectionPoint, Integer> vis =  new HashMap<ConnectionPoint, Integer>() ; 
	void initVis () {
		for( ConnectionPoint cp : links.keySet()){
			vis.put(cp, 0) ;
		}
	}
	ArrayList<Integer> cost = new ArrayList< Integer>(); 
	ArrayList< ArrayList<ConnectionPoint>> mutipath = 
			new ArrayList< ArrayList<ConnectionPoint>>() ;
	int total_cost = 0 ; 
	int total_phero = 0 ;
	ArrayList<Integer> cost_total= new ArrayList<Integer> () ;
	ArrayList<Integer> phero_total = new ArrayList<Integer>() ; 
	void computepath(ConnectionPoint src 
			, ConnectionPoint dst ,
			ArrayList<ConnectionPoint> path
			){
		
		 
		vis.put(src,1) ; 
		path.add(src) ;
		for (pair adj : links.get(src)){
			if (vis.get(adj.dpid) == 0){
				path.add(adj.dpid) ; 
				total_cost +=adj.length ; 
				cost.add(adj.length) ; 
				total_phero +=50 ; 
				 computepath(adj.dpid , dst ,path);
			}
		}
		if (src == dst ){
			mutipath.add(path) ;
			cost_total.add(total_cost) ; 
			phero_total.add(total_phero) ; 
		}
		path.remove(path.size() -1 );
		if (path.size() != 0){ 
			total_cost -= cost.remove(cost.size()-1);
			total_phero-=50 ; 
		}
		vis.put(src , 0) ;
		
	
	}*/
	
	
	
}

		
		
			

