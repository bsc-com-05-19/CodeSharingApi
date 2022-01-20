package com.example.CodePlatformApi;

import com.example.CodePlatformApi.FileUpload.FileRepository;
import com.example.CodePlatformApi.FileUpload.FileUpload;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CodePlatformApiApplicationTests {

	@Autowired
	private FileRepository repository;
	@Autowired
	private TestEntityManager entityManager;

	@Test
	void TestInsertDocument() throws IOException {
		File file = new File("C:\\Users\\USER\\Documents\\Document.doc");
		FileUpload fileUpload = new FileUpload();
		fileUpload.setFilename(file.getName());

		byte [] bytes =  Files.readAllBytes(file.toPath());
		fileUpload.setContent(bytes);
		long filesize = bytes.length;
		fileUpload.setSize(filesize);

		FileUpload savedoc = repository.save(fileUpload);
		FileUpload existdoc = entityManager.find(FileUpload.class, savedoc.getId());

		assertThat(existdoc.getSize()).isEqualTo(filesize);
	}

	private <SELF extends AbstractBigDecimalAssert<SELF>> AbstractBigDecimalAssert assertThat(long size) {
		return null;
	}

}
