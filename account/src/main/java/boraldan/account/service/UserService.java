package boraldan.account.service;

import boraldan.account.repository.UserRepository;
import boraldan.entityrepo.dto.CreatUserDto;
import boraldan.entityrepo.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    @Getter
    @Value("${user.photo.default-path}")
    private String defaultPhotoPath;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    /**
     * Получает список всех пользователей из базы данных.
     *
     * @return список пользователей.
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Получает пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя.
     * @return Optional<User> с найденным пользователем или пустой Optional.
     */
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    /**
     * Сохраняет нового пользователя в базе данных.
     *
     * @param user пользователь для сохранения.
     * @return сохраненный пользователь.
     */
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Обновляет данные существующего пользователя в базе данных.
     *
     * @param user пользователь для обновления.
     * @return обновленный пользователь.
     */
    @Transactional
    public User update(User user) {
        return userRepository.save(user);
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя для удаления.
     */
    @Transactional
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    /**
     * Получает пользователя по имени пользователя, игнорируя регистр.
     *
     * @param username имя пользователя.
     * @return Optional<User> с найденным пользователем или пустой Optional.
     */
    public Optional<User> findByUsernameIgnoreCase(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    /**
     * Загружает фотографию пользователя.
     *
     * @param photo файл фотографии.
     * @return путь к загруженной фотографии или путь к фотографии по умолчанию, если файл пустой.
     */
    public String downloadPhoto(MultipartFile photo) {
        return photo.isEmpty() ? defaultPhotoPath : fileStorageService.storeFile(photo);
    }

    /**
     * Обновляет фотографию пользователя.
     *
     * @param photo файл новой фотографии.
     * @return путь к новой фотографии.
     */
    public String updatePhoto(MultipartFile photo) {
        return fileStorageService.storeFile(photo);
    }

    /**
     * Обновляет поля пользователя на основе данных из DTO.
     *
     * @param user пользователь, поля которого необходимо обновить.
     * @param creatUserDto DTO с новыми данными пользователя.
     * @return обновленный пользователь.
     */
    public User updateUserFields(User user, CreatUserDto creatUserDto) {
        if (creatUserDto.getLastName() != null) {
            user.setLastName(creatUserDto.getLastName());
        }
        if (creatUserDto.getFirstName() != null) {
            user.setFirstName(creatUserDto.getFirstName());
        }
        if (creatUserDto.getMiddleName() != null) {
            user.setMiddleName(creatUserDto.getMiddleName());
        }
        if (creatUserDto.getDateBirth() != null) {
            user.setDateBirth(creatUserDto.getDateBirth());
        }
        if (creatUserDto.getEmail() != null) {
            user.setEmail(creatUserDto.getEmail());
        }
        if (creatUserDto.getPhoneNumber() != null) {
            user.setPhoneNumber(creatUserDto.getPhoneNumber());
        }
        return user;
    }
}