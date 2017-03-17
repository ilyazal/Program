package bsuir.kyrs.command.Impl;

import bsuir.kyrs.bean.Request;
import bsuir.kyrs.bean.Response;
import bsuir.kyrs.bean.entity.Export;
import bsuir.kyrs.command.Command;
import bsuir.kyrs.dao.MySQLDAO;
import bsuir.kyrs.dao.daopr.ExportDaoPr;
import bsuir.kyrs.dao.exception.DAOException;

import java.util.List;


public class ExportsCmd implements Command {
    @Override
    public Response execute(Request request) {
        Response response = new Response();
            try {
                MySQLDAO<Export, String> dao = ExportDaoPr.getInstance();
                List<Export> dbExport = dao.select();
                response.setAttribute("exports",dbExport);
            } catch(DAOException e){
                response.setError(true);
                System.out.println(e.getMessage());
                response.setMessage("Ой, неправильно!");
            }
            return response;
        }
    }