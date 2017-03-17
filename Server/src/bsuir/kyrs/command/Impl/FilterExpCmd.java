package bsuir.kyrs.command.Impl;

import bsuir.kyrs.bean.Request;
import bsuir.kyrs.bean.Response;
import bsuir.kyrs.bean.entity.Export;
import bsuir.kyrs.command.Command;
import bsuir.kyrs.dao.AccountingDAO;
import bsuir.kyrs.dao.daopr.FilterExportDaoPr;
import bsuir.kyrs.dao.exception.DAOException;

import java.util.List;


public class FilterExpCmd implements Command {
    @Override
    public Response execute(Request request) {
        Response response = new Response();
        try {
            AccountingDAO<Export, String> dao = FilterExportDaoPr.getInstance();
            String factory= String.valueOf(request.getParameter("filterdateexp"));
            List<Export> dbExport = dao.selectByFactory(factory);
            response.setAttribute("filterexp",dbExport);
        } catch(DAOException e){
            response.setError(true);
            System.out.println(e.getMessage());
            response.setMessage("Ой, неправильно!");
        }
        return response;
    }
}
