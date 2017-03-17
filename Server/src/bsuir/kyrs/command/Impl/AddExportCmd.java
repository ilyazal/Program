package bsuir.kyrs.command.Impl;

import bsuir.kyrs.bean.Request;
import bsuir.kyrs.bean.Response;
import bsuir.kyrs.bean.entity.Export;
import bsuir.kyrs.command.Command;
import bsuir.kyrs.dao.AccountingDAO;
import bsuir.kyrs.dao.daopr.ExportAddPr;
import bsuir.kyrs.dao.exception.DAOException;

import java.time.LocalDate;


public class AddExportCmd implements Command {
    @Override
    public Response execute(Request request) {
        Response response = new Response();
        try{
        AccountingDAO<Export, String> dao = ExportAddPr.getInstance();
        LocalDate date2= (LocalDate) request.getParameter("date2e");
        String called = (String) request.getParameter("callede");
        String material= (String) request.getParameter("materiale");
        int quantity = (int) request.getParameter("quantitye");
            String factory= (String) request.getParameter("factory_ex");
            int monye=(int ) request.getParameter("monye_ex");
        dao.insertExport(called,material,quantity,date2,factory,monye);
    } catch(DAOException e){
        response.setError(true);
        System.out.println(e.getMessage());
        response.setMessage("Ой, неправильно!");
    }
        return response;
    }
}
