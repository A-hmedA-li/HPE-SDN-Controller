package com.a.hola.impl;

import static com.hp.of.lib.instr.ActionFactory.createAction;



import static com.hp.of.lib.instr.InstructionFactory.createMutableInstruction;
import static com.hp.of.lib.match.FieldFactory.createBasicField;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.hp.of.lib.ProtocolVersion;
import com.hp.of.lib.dt.BufferId;
import com.hp.of.lib.dt.TableId;
import com.hp.of.lib.instr.ActionType;
import com.hp.of.lib.instr.InstrMutableAction;
import com.hp.of.lib.instr.Instruction;
import com.hp.of.lib.instr.InstructionType;
import com.hp.of.lib.match.Match;
import com.hp.of.lib.match.MatchFactory;
import com.hp.of.lib.match.MutableMatch;
import com.hp.of.lib.match.OxmBasicFieldType;
import com.hp.of.lib.msg.FlowModCommand;
import com.hp.of.lib.msg.FlowModFlag;
import com.hp.of.lib.msg.MessageFactory;
import com.hp.of.lib.msg.MessageType;
import com.hp.of.lib.msg.OfmFlowMod;
import com.hp.of.lib.msg.OfmMutableFlowMod;
import com.hp.of.lib.msg.Port;
import com.hp.util.ip.BigPortNumber;
import com.hp.util.ip.EthernetType;
import com.hp.util.ip.IpAddress;
import com.hp.util.ip.MacAddress;
import com.hp.util.ip.PortNumber;

public class packetout {

	public  ProtocolVersion pv = ProtocolVersion.V_1_3;
	public long cookie = 0x00002468 ; 
	public TableId tableid = TableId.valueOf(0);
	public int idleTimeouit = 15000 ; 
	public int hardTimeOut = 15000 ; 
	public int priorty = 60001 ; 
	public BufferId bufferID = BufferId.NO_BUFFER; 
	public Set<FlowModFlag> flags = EnumSet.of (
			FlowModFlag.SEND_FLOW_REM, 
			FlowModFlag.CHECK_OVERLAP , 
			FlowModFlag.NO_BYTE_COUNTS
			); 
	public MacAddress MAC = MacAddress.valueOf("00001e:000000") ; 
	public MacAddress MAC_MASK = MacAddress.valueOf("ffffff:000000");
	public PortNumber SMTP_PORT = PortNumber.valueOf(25) ; 
	public MacAddress MAC_DEST = MacAddress.BROADCAST;
	
	
	public  IpAddress dstIp ;
	public  IpAddress srcIp ;
	public  BigPortNumber outport ; 
	public  BigPortNumber inport ; 
	packetout( BigPortNumber outport2 , BigPortNumber inport2,  
			IpAddress target, IpAddress  sender ){
		outport = outport2 ; 
		inport = inport2 ; 
		dstIp = target ; 
		srcIp = sender  ;
	}
	packetout() {
		
	}
	public  OfmFlowMod flowModCreathion ( ){
		
		OfmMutableFlowMod fm = (OfmMutableFlowMod) MessageFactory.create(pv , 
				
				MessageType.FLOW_MOD , FlowModCommand.ADD
				) ; 
		fm.cookie(cookie).tableId(tableid).priority(priorty).idleTimeout(idleTimeouit)
		.hardTimeout(hardTimeOut).bufferId(bufferID)
		.flowModFlags(flags).command(FlowModCommand.ADD) 
		.match(createMatch());
		for (Instruction ins : createInstructions())
			fm.addInstruction(ins) ;
		System.out.println("flow mod creation ") ; 
		fm.outPort(Port.NORMAL);
		return (OfmFlowMod) fm.toImmutable () ; 
	}
	public Match createMatch(){
		
		MutableMatch mm = MatchFactory.createMatch (pv) ;
				
			
					
			
					mm.addField(createBasicField(pv ,
							OxmBasicFieldType.IN_PORT , inport)) ; 
					mm.addField(createBasicField(pv , 
							OxmBasicFieldType.IPV4_SRC , srcIp 
							)) ; 
					mm.addField(createBasicField(pv , 
							OxmBasicFieldType.IPV4_DST , dstIp 
							)) ; 
					mm.addField(createBasicField(pv , 
							OxmBasicFieldType.ETH_TYPE , EthernetType.IPv4 
							)) ; 
				
	
		return (Match) mm.toImmutable() ; 
				 
	}
	
	
	public List<Instruction> createInstructions (){
		List <Instruction> result = new ArrayList<Instruction> () ; 
	//	result.add(createInstruction(pv , InstructionType.WRITE_METADATA 
	//			,INS_META_DATA , INS_META_MASK )) ; 
		
		InstrMutableAction apply = createMutableInstruction(pv 
				, InstructionType.APPLY_ACTIONS);
	//	apply.addAction(createAction ( pv , ActionType.DEC_NW_TTL))
	//	.addAction(createActionSetField(pv , ETH_DST,MAC_DEST))
	//	.addAction(createActionSetField(pv , IPV4_DST , IP_DEST) ) ; 
		
	//	apply.addAction(createAction(pv, ActionType.OUTPUT , Port.NORMAL));
	
		
			apply.addAction(createAction(pv , ActionType.OUTPUT , outport));
			
		
		result.add((Instruction)apply.toImmutable() ) ;
		return result ; 

			}
}
