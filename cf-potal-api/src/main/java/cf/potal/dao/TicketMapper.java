package cf.potal.dao;

import java.util.List;

import cf.bean.Member;
import cf.bean.Ticket;

public interface TicketMapper {
	int deleteByPrimaryKey(Integer id);

	Ticket selectByPrimaryKey(Integer id);

	List<Ticket> selectAll();

	Ticket getTicketByMemberId(Integer memberid);

	void saveTicket(Ticket ticket);

	void updatePstep(Ticket ticket);

	void updatePiidAndPstep(Ticket ticket);

	Member getMemberByPiid(String processInstanceId);

	void updateStatus(Member member);

	Ticket selectByMemberId(int id);

	void updateTicket(Ticket ticket);
}
