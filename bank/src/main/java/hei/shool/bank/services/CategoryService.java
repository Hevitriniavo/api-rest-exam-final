package hei.shool.bank.services;

import hei.shool.bank.dtos.responses.CategoryResponse;
import hei.shool.bank.entities.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> findAll();

    CategoryResponse createOrUpdate(Category category);


    CategoryResponse deleteById(Long id);

    CategoryResponse findById(Long id);
}
