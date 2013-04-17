package org.softwaresynthesis.mytalk.server.message;

import org.softwaresynthesis.mytalk.server.dao.MessageDAO;

public class GenerateFileName 
{
	public Long next()
	{
		Long max = null;
		MessageDAO messageDAO = new MessageDAO();
		max = messageDAO.getMaxKey();
		max = max + 1;
		return max;
	}
}