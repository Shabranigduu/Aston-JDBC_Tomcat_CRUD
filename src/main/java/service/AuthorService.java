package service;

import dao.AuthorDAO;
import dao.PublisherDAO;
import dto.AuthorDTO;
import entity.Author;
import mappers.AuthorReadMapper;
import mappers.AuthorWriteUpdateMapper;

import java.util.Optional;

public class AuthorService {

    private static final AuthorService INSTANCE = new AuthorService();

    public static AuthorService getInstance() {
        return INSTANCE;
    }

    private final AuthorReadMapper authorReadMapper = new AuthorReadMapper();
    private final AuthorWriteUpdateMapper authorWriteUpdateMapper = new AuthorWriteUpdateMapper();

    public Author findByNameOrElseAdd(String name) {
        Author author = AuthorDAO.getInstance().findByName(name);
        if (author == null) {
            return AuthorDAO.getInstance().add(name);
        }
        return author;
    }

    public Author findById(Integer id) {
        return AuthorDAO.getInstance().getById(id);
    }

    public Author add(Author author) {
        return AuthorDAO.getInstance().add(author);
    }

    public Author add(AuthorDTO authorDTO) {
        return AuthorDAO.getInstance().add(authorWriteUpdateMapper.map(authorDTO));
    }

    public AuthorDTO getAuthorDTO(Author author) {
        if (author == null) {
            return null;
        }
        return authorReadMapper.map(author);
    }

    public Author getAuthor(AuthorDTO authorDTO) {
        if (authorDTO == null) {
            return null;
        }
        return authorWriteUpdateMapper.map(authorDTO);
    }

    public boolean update(Integer id, AuthorDTO authorDTO) {
        Author author = AuthorDAO.getInstance().getById(id);
        if (author == null) {
            return false;
        }
        Author updateFrom = authorWriteUpdateMapper.map(authorDTO);
        author.setFirstname(updateFrom.getFirstname());
        author.setLastname(updateFrom.getLastname());
        AuthorDAO.getInstance().update(author);
        return true;
    }

    public boolean delete(Integer id) {
        return AuthorDAO.getInstance().delete(id);
    }
}
