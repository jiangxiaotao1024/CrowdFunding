package cf.potal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cf.bean.Member;
import cf.bean.Ticket;
import cf.potal.dao.TicketMapper;
import cf.potal.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	@Autowired
	TicketMapper ticketMapper;

	public void saveTicket(Ticket ticket2) {
		// TODO Auto-generated method stub
		ticketMapper.saveTicket(ticket2);
	}

	public Ticket queryTicketByMemberId(Integer id) {
		// TODO Auto-generated method stub
		Ticket ticket = ticketMapper.selectByMemberId(id);
		return ticket;
	}

	public void updateTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		ticketMapper.updateTicket(ticket);
	}

	public void updateStatus(Member member) {
		// TODO Auto-generated method stub
		ticketMapper.updateStatus(member);
	}

	public Member queryMemberByPiid(String processInstanceId) {
		// TODO Auto-generated method stub
		Member member = ticketMapper.getMemberByPiid(processInstanceId);
		return member;
	}

}
