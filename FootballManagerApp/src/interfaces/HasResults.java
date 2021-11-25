package interfaces;

import java.util.LinkedList;

import events.Result;

/**
 * A pointer interface to identify classes that contain results
 * @author Jack
 *
 */
public interface HasResults {
	
	public LinkedList<Result> getResults();

}
