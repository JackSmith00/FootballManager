package interfaces;

import java.util.LinkedList;

import events.Result;

/**
 * A pointer interface to identify classes that contain results
 * @author Jack
 *
 */
public interface HasResults {
	
	/**
	 * @return a linked list if all results stored by the object
	 */
	public LinkedList<Result> getResults();

}
