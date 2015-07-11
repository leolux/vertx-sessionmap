package node.util;

import io.vertx.core.Vertx;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.Shareable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Utility class for session data with a built-in timeout.
 *
 * @author Mathias
 * @see https://groups.google.com/forum/#!topic/vertx/CBqzoEoRolo
*/
public class SessionMap extends ConcurrentHashMap<String, String> implements Shareable {

  private static final long serialVersionUID = -199423891294997167L;

  private final LocalMap<String, SessionMap> holderMapReference;

  private final String sessionId;

  private final Vertx vertx;

  private final AtomicLong timerId = new AtomicLong(-1);

  public SessionMap(LocalMap<String, SessionMap> holderMapReference, String sessionId, Vertx vertx) {
    this.holderMapReference = holderMapReference;
    this.sessionId = sessionId;
    this.vertx = vertx;
    this.timerId.getAndSet(setTimer());
  }

  /**
   * 
   * @param timerId
   * @return old timerId
   */
  private long setTimer() {
    return vertx.setTimer(SessionHandler.DEFAULT_SESSION_TIMEOUT, id -> {
      this.clear();
      holderMapReference.remove(sessionId);
    });
  }

  private void touchSession() {
    long id = this.timerId.getAndSet(setTimer());
    if (id != -1) {
      vertx.cancelTimer(id);
    }
  }

  @Override
  public String get(Object key) {
    touchSession();
    return super.get(key);
  }

  public String put(String key, Object value) {
    return super.put(key, String.valueOf(value));
  }



}
