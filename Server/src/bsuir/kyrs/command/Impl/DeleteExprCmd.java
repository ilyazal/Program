package bsuir.kyrs.command.Impl;

import bsuir.kyrs.bean.Request;
import bsuir.kyrs.bean.Response;
import bsuir.kyrs.bean.entity.Export;
import bsuir.kyrs.command.Command;
import bsuir.kyrs.dao.MySQLDAO;
import bsuir.kyrs.dao.daopr.ExportDelDaoPr;
import bsuir.kyrs.dao.exception.DAOException;


public class DeleteExprCmd implements Command {
    @Override
    public Response execute(Request request) {
        Response response = new Response();
        try {
            MySQLDAO<Export, String> dao = ExportDelDaoPr.getInstance();
            int id= (Integer) request.getParameter("deleteexp");
            dao.delete(id);
            //response.setAttribute("delexp",delExport);
        } catch(DAOException e){
            response.setError(true);
            System.out.println(e.getMessage());
            response.setMessage("Ой, неправильно!");
        }
        return response;
    }
    }
