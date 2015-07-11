# vertx-sessionmap
The class SessionMap is a utility class which allows session data to be stored in LocalMap with a built-in timeout.

Example:
    LocalMap<String, SessionMap> map = vertx.sharedData().getLocalMap(NodeConstants.LOCAL_MAP_SESSION_ID);
    final String sessionId = routingContext.session().id();
    SessionMap sessionMap = map.get(sessionId);
    if (sessionMap == null) {
      sessionMap = new SessionMap(map, sessionId, vertx);
      map.put(sessionId, sessionMap);
    }
	map.put("key","value");
