package bsuir.kyrs.command;

import bsuir.kyrs.bean.Request;
import bsuir.kyrs.bean.Response;
import bsuir.kyrs.command.Impl.*;
import bsuir.kyrs.command.constant.Cmd;

import java.util.HashMap;

public class CommandManager {
    private static CommandManager instance = new CommandManager();
    private HashMap<String, Command> commands;

    public static CommandManager getInstance(){
        return instance;
    }

    private CommandManager(){
        commands = new HashMap<>();
        commands.put(Cmd.SIGN_IN_COMMAND, new SingInCmd());
        commands.put(Cmd.SELECT_ONLY_IMPORTS,new ImportsCmd());
        commands.put(Cmd.SELECT_ONLY_EXPORT,new ExportsCmd());
        commands.put(Cmd.EXIT,new ExitCmd());
        commands.put(Cmd.DELETE_EXPORT,new DeleteExprCmd());
        commands.put(Cmd.DELETE_IMPORT,new DeleteImprCmd());
        commands.put(Cmd.ADD_IMPORT,new AddImportCmd());
        commands.put(Cmd.ADD_EXPORT, new AddExportCmd());
        //commands.put(Cmd.DO_FILTER,new FilterCmd());
        commands.put(Cmd.DO_FILTER_EXPORT,new FilterExpCmd());
        commands.put(Cmd.DO_FILTER,new FilterCmd());
    }

    public Response execute(Request request){
        String command = request.getCommand();
        return commands.get(command).execute(request);
    }
}
