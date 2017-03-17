package bsuir.kyrs.command.Impl;

import bsuir.kyrs.bean.Request;
import bsuir.kyrs.bean.Response;
import bsuir.kyrs.bean.entity.Import;
import bsuir.kyrs.command.Command;
import bsuir.kyrs.dao.AccountingDAO;
import bsuir.kyrs.dao.daopr.FilterImportDaoPr;
import bsuir.kyrs.dao.exception.DAOException;

import java.util.List;


public class FilterCmd implements Command {
    @Override
    public Response execute(Request request) {
        Response response = new Response();
        try {
            AccountingDAO<Import, String> dao = FilterImportDaoPr.getInstance();
            String fac= String.valueOf(request.getParameter("filterdate"));
            List<Import> dbImport = dao.selectByFactory(fac);
            response.setAttribute("filterimpr",dbImport);
        } catch(DAOException e){
            response.setError(true);
            System.out.println(e.getMessage());
            response.setMessage("Ой, неправильно!");
        }
        return response;
    }
    }
