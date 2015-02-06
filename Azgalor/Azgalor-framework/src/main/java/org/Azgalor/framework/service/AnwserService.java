package org.Azgalor.framework.service;

import org.Azgalor.framework.entities.Anwsers;
import org.Azgalor.framework.entities.Messages;

/**
 * 答卷service
 * 
 * @author ming
 *
 */

public interface AnwserService {
	default String search() {
		return "";
	}

	public Messages anwser(Anwsers anwser);

}
