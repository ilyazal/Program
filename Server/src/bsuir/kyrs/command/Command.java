package bsuir.kyrs.command;

import bsuir.kyrs.bean.Request;
import bsuir.kyrs.bean.Response;


public interface Command {
        Response execute(Request request);
}
