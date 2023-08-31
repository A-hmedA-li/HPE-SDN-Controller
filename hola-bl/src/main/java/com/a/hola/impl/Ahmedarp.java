package com.a.hola.impl;

import com.hp.of.lib.match.Match;
import com.hp.of.lib.match.MatchFactory;
import com.hp.of.lib.match.MutableMatch;
import com.hp.of.lib.match.OxmBasicFieldType;
import com.hp.of.lib.msg.Port;
import com.hp.util.ip.BigPortNumber;
import com.hp.util.ip.MacAddress;

import static com.hp.of.lib.instr.ActionFactory.createAction;
import static com.hp.of.lib.instr.InstructionFactory.createMutableInstruction;
import static com.hp.of.lib.match.FieldFactory.createBasicField;

import java.util.ArrayList;
import java.util.List;

import com.hp.of.lib.instr.ActionType;
import com.hp.of.lib.instr.InstrMutableAction;
import com.hp.of.lib.instr.Instruction;
import com.hp.of.lib.instr.InstructionType;
public class Ahmedarp extends packetout {
	BigPortNumber outport;
	
	Ahmedarp(MacAddress src , MacAddress dst){
		super()  ; 
		super.MAC = src;
		super.MAC_DEST = dst ; 
		super.priorty = 30000 ; 
	}
	Ahmedarp(BigPortNumber outp , BigPortNumber inport ,
			MacAddress src , MacAddress dst){
		super()  ; 
		super.MAC = src;
		super.MAC_DEST = dst ; 
		super.priorty = 30000 ; 
		outport = outp; 
	}
	
	
	MacAddress empty =  MacAddress.valueOf("000000:000000") ; 
	
	MacAddress mac = MacAddress.BROADCAST ; 
	
	
	@Override 
	public Match createMatch(){
		if (!super.MAC_DEST.equals(empty))
			mac = super.MAC_DEST;
		MutableMatch mm = MatchFactory.createMatch(pv)  ; 
		mm.addField(createBasicField(pv, OxmBasicFieldType.ETH_SRC , super.MAC)) ; 
		mm.addField(createBasicField(pv,
				OxmBasicFieldType.ETH_DST , mac)) ; 
		
		mac = MacAddress.BROADCAST;
		return (Match) mm.toImmutable(); 
	}
	@Override
	public List<Instruction> createInstructions (){
		List <Instruction> result = new ArrayList<Instruction> () ; 
		InstrMutableAction apply = createMutableInstruction(pv 
				, InstructionType.APPLY_ACTIONS);
		if (!super.MAC_DEST.equals(empty) && outport != null){
			
		apply.addAction(createAction(pv , ActionType.OUTPUT , outport));
	
		}
		else
		apply.addAction(createAction(pv, ActionType.OUTPUT , Port.FLOOD)) ; 
		
		result.add((Instruction)apply.toImmutable() ) ;
		return result ; 
	}
/*	public static void main (String [] ar){
		MacAddress a =  MacAddress.valueOf("000000:000000") ; 
		MacAddress b =  MacAddress.valueOf("000000:000000") ;
		System.out.println(a.equals(b)) ; 
		
	}*/
}
