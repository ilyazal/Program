package bsuir.kyrs.command.Impl;

import bsuir.kyrs.bean.Request;
import bsuir.kyrs.bean.Response;
import bsuir.kyrs.command.Command;
import bsuir.kyrs.server.Server;
import bsuir.kyrs.server.handler.RequestHandler;


public class ExitCmd implements Command {
    @Override
    public Response execute(Request request) {
        RequestHandler handler = (RequestHandler) request.getParameter("requesthandler");
        Response response = new Response();
        handler.disconnectClient();
        Server.disconnectClient();
        return response;
    }}
