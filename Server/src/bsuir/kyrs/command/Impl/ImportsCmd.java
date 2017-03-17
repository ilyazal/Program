package bsuir.kyrs.command.Impl;

import bsuir.kyrs.bean.Request;
import bsuir.kyrs.bean.Response;
import bsuir.kyrs.bean.entity.Import;
import bsuir.kyrs.command.Command;
import bsuir.kyrs.dao.MySQLDAO;
import bsuir.kyrs.dao.daopr.ImportDaoPr;
import bsuir.kyrs.dao.exception.DAOException;

import java.util.List;


public class ImportsCmd implements Command {
    @Override
    public Response execute(Request request) {
        Response response = new Response();
        try {
            MySQLDAO<Import, String> dao = ImportDaoPr.getInstance();
            List<Import> dbImport = dao.select();
            dbImport.forEach(System.out::println);
            response.setAttribute("imports",dbImport);
        } catch(DAOException e){
            response.setError(true);
            System.out.println(e.getMessage());
            response.setMessage("Ой, неправильно!");
        }
        return response;
    }
}
