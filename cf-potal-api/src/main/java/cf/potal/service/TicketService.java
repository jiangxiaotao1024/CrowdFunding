package cf.potal.service;

import cf.bean.Member;
import cf.bean.Ticket;

public interface TicketService {
	void saveTicket(Ticket ticket2);

	Ticket queryTicketByMemberId(Integer id);

	void updateTicket(Ticket ticket);

	void updateStatus(Member member);

	Member queryMemberByPiid(String processInstanceId);

}
