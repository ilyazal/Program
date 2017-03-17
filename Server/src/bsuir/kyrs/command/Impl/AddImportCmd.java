package bsuir.kyrs.command.Impl;

import bsuir.kyrs.bean.Request;
import bsuir.kyrs.bean.Response;
import bsuir.kyrs.bean.entity.Import;
import bsuir.kyrs.command.Command;
import bsuir.kyrs.dao.AccountingDAO;
import bsuir.kyrs.dao.daopr.ImportAddPr;
import bsuir.kyrs.dao.exception.DAOException;

import java.time.LocalDate;


public class AddImportCmd implements Command {
    @Override
    public Response execute(Request request) {
        Response response = new Response();
        try {
            AccountingDAO<Import, String> dao = ImportAddPr.getInstance();

           LocalDate date2= (LocalDate) request.getParameter("date2");
           String called = (String) request.getParameter("called");
           String material= (String) request.getParameter("material");
            String factory= (String) request.getParameter("factory");
           int quantity = (int) request.getParameter("quantity");
            int monye=(int ) request.getParameter("monye");
            dao.insertImport(called,material,quantity,date2,factory,monye);
        } catch(DAOException e){
            response.setError(true);
            System.out.println(e.getMessage());
            response.setMessage("Ой, неправильно!");
        }
        return response;
    }
}
