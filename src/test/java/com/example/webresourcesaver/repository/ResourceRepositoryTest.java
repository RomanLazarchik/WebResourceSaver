package com.example.webresourcesaver.repository;

import com.example.webresourcesaver.model.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ResourceRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ResourceRepository resourceRepository;

    @Test
    public void testSaveAndFindResource() {
        // given
        Resource resource = new Resource();
        resource.setUrl("http://example.com");
        resource.setFilePath("C:\\downloads\\test-resource.txt");

        entityManager.persistAndFlush(resource);

        // when
        Resource foundResource = resourceRepository.findById(resource.getId()).orElse(null);

        // then
        assertThat(foundResource).isNotNull();
        assertThat(foundResource.getUrl()).isEqualTo(resource.getUrl());
        assertThat(foundResource.getFilePath()).isEqualTo(resource.getFilePath());
    }
}



