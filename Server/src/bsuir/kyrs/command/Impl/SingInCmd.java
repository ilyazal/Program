package bsuir.kyrs.command.Impl;

import bsuir.kyrs.bean.Request;
import bsuir.kyrs.bean.Response;
import bsuir.kyrs.bean.entity.User;
import bsuir.kyrs.command.Command;
import bsuir.kyrs.dao.MySQLDAO;
import bsuir.kyrs.dao.daopr.UserDaoPr;
import bsuir.kyrs.dao.exception.DAOException;

public class SingInCmd implements Command {

    @Override
    public Response execute(Request request) {
        User user = (User) request.getParameter("user");
        Response response = new Response();

        try {
            MySQLDAO<User, String> dao = UserDaoPr.getInstance();

            User dbUser = dao.selectByKey(user.getUsername());
            if(dbUser == null || user.getPassword() != dbUser.getPassword()){
                response.setError(true);
                response.setMessage("Неверно введённые данные");

            } else {
                response.setAttribute("user", dbUser);
            }
        } catch(DAOException e){
            response.setError(true);
            System.out.println(e.getMessage());
            response.setMessage("Ой, неправильно!");
        }

        return response;
    }
}

