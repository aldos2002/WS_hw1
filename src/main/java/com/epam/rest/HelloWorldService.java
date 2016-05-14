package com.epam.rest;

import com.epam.dao.UserDao;
import com.epam.entities.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class HelloWorldService {
    private UserDao userDao = new UserDao();

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_JSON)
    public User createUser(User user) {
        userDao.addUser(user);
        return user;
    }

    @GET
    @Path("/{userid}")
    @Produces(MediaType.APPLICATION_XML)
    public User readUser(@PathParam("userid") String user) {
        return userDao.readUser(user);
    }

    @PUT
    @Path("/{userid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User updateUser(@PathParam("userid") String userid, User user) {
        userDao.updateUser(userid, user);
        return user;
    }

    @DELETE
    @Path("/{userid}")
    @Produces(MediaType.APPLICATION_XML)
    public Response deleteUser(@PathParam("userid") String login) {
        int result = userDao.deleteUser(login);
        if (result == 1) {
            return Response.status(200).entity(login + " deleted.").build();
        }
        return Response.status(500).entity("User not found.").build();
    }
}