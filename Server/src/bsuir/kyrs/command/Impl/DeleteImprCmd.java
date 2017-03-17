package bsuir.kyrs.command.Impl;

import bsuir.kyrs.bean.Request;
import bsuir.kyrs.bean.Response;
import bsuir.kyrs.bean.entity.Import;
import bsuir.kyrs.command.Command;
import bsuir.kyrs.dao.MySQLDAO;
import bsuir.kyrs.dao.daopr.ImportDelDaoPr;
import bsuir.kyrs.dao.exception.DAOException;


public class DeleteImprCmd implements Command {
    @Override
    public Response execute(Request request) {
        Response response = new Response();
        try {
            MySQLDAO<Import, String> dao = ImportDelDaoPr.getInstance();
            int id= (Integer) request.getParameter("deleteimpr");
             dao.delete(id);
            //response.setAttribute("delimpr",delExport);
        } catch(DAOException e){
            response.setError(true);
            System.out.println(e.getMessage());
            response.setMessage("Ой, неправильно!");
        }
        return response;
    }
    }

