package hei.shool.bank.services.implement;

import hei.shool.bank.dtos.responses.CategoryResponse;
import hei.shool.bank.entities.Category;
import hei.shool.bank.repositories.CategoryRepository;
import hei.shool.bank.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplement implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImplement(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse createOrUpdate(Category category) {
        Category savedCategory = categoryRepository.saveOrUpdate(category);
        return convertToDTO(savedCategory);
    }

    @Override
    public CategoryResponse deleteById(Long id) {
        Category deletedCategory = categoryRepository.deleteById(id);
        return convertToDTO(deletedCategory);
    }

    @Override
    public CategoryResponse findById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            return convertToDTO(category);
        }
        return null;
    }

    private CategoryResponse convertToDTO(Category category) {
        return new CategoryResponse(category.getId(), category.getName().getLabel());
    }
}
