package com.codegym.weather.config;

import com.codegym.weather.model.AppUser;
import com.codegym.weather.model.Weather;
import com.codegym.weather.repository.UserRepository;
import com.codegym.weather.repository.WeatherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(WeatherRepository weatherRepository,
                                   UserRepository userRepository,
                                   PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                userRepository.saveAll(List.of(
                        new AppUser("user", passwordEncoder.encode("123456"), "ROLE_USER"),
                        new AppUser("admin", passwordEncoder.encode("admin123"), "ROLE_ADMIN")
                ));
            }

            if (weatherRepository.count() == 0) {
                LocalDateTime now = LocalDateTime.now();
                weatherRepository.saveAll(List.of(
                        new Weather("hanoi", "Hà Nội", 24.5, 72, 11.0,
                                "Có mây", "Trời mát mẻ, có mây rải rác, không mưa.", now),
                        new Weather("hochiminh", "TP. Hồ Chí Minh", 32.0, 68, 14.5,
                                "Nắng nóng", "Trời nắng vào ban ngày, chiều tối có thể có mưa rào nhẹ.", now),
                        new Weather("danang", "Đà Nẵng", 28.0, 75, 16.0,
                                "Nắng nhẹ", "Gió biển mát mẻ, trời nắng nhẹ, thích hợp hoạt động ngoài trời.", now),
                        new Weather("hue", "Huế", 26.0, 82, 9.5,
                                "Mưa nhẹ", "Nhiều mây, có mưa phùn rải rác trong ngày.", now),
                        new Weather("nhatrang", "Nha Trang", 29.5, 70, 18.0,
                                "Nắng đẹp", "Trời trong xanh, nắng đẹp, gió đông nam nhẹ.", now),
                        new Weather("dalat", "Đà Lạt", 18.5, 85, 8.0,
                                "Se lạnh", "Sáng sớm có sương mù nhẹ, trưa hửng nắng, tối se lạnh.", now),
                        new Weather("haiphong", "Hải Phòng", 25.0, 78, 15.0,
                                "Nhiều mây", "Trời nhiều mây, có gió nhẹ từ biển thổi vào.", now),
                        new Weather("cantho", "Cần Thơ", 31.0, 74, 10.5,
                                "Nắng gián đoạn", "Ngày nắng gián đoạn, chiều tối có khả năng mưa dông cục bộ.", now)
                ));
            }
        };
    }
}
