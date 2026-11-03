package com.codegym.service;

import com.codegym.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
        private static final Map<Long, Product> products = new LinkedHashMap<>();

        static {
                products.put(1L, new Product(
                                1L,
                                "SP4101",
                                "Bó Hoa Tươi Nắng Mai",
                                350000,
                                420000,
                                "Bó hoa kết hợp giữa hoa hồng vàng, cúc họa mi và lá bạc tươi mát. Tặng kèm thiệp chúc mừng miễn phí và giao hàng nhanh trong 2 giờ nội thành.",
                                "https://images.unsplash.com/photo-1561181286-d3fee7d55364?auto=format&fit=crop&w=600&q=80"));
                products.put(2L, new Product(
                                2L,
                                "SP4102",
                                "Bó Hoa Hồng Đỏ Tình Yêu",
                                450000,
                                550000,
                                "Bó hoa gồm 20 bông hồng đỏ Đà Lạt tuyển chọn phối cùng hoa baby trắng tinh khôi, biểu tượng cho tình yêu nồng nàn và lãng mạn.",
                                "https://images.unsplash.com/photo-1518709779341-56cf4535e94b?auto=format&fit=crop&w=600&q=80"));
                products.put(3L, new Product(
                                3L,
                                "SP4103",
                                "Giỏ Hoa Hướng Dương Rực Rỡ",
                                500000,
                                600000,
                                "Giỏ hoa hướng dương kết hợp hồng kem mang thông điệp về sự thành công, niềm tin và nguồn năng lượng tích cực cho ngày đặc biệt.",
                                "https://images.unsplash.com/photo-1597848212624-a19eb35e2651?auto=format&fit=crop&w=600&q=80"));
                products.put(4L, new Product(
                                4L,
                                "SP4104",
                                "Hộp Hoa Tulip Hồng Pastel",
                                680000,
                                790000,
                                "Hộp hoa Tulip nhập khẩu sắc hồng pastel dịu dàng, sang trọng, phù hợp làm quà tặng sinh nhật, kỷ niệm hoặc chúc mừng người thân.",
                                "https://images.unsplash.com/photo-1520763185298-1b434c919102?auto=format&fit=crop&w=600&q=80"));
                products.put(5L, new Product(
                                5L,
                                "SP4105",
                                "Bó Hoa Cẩm Tú Cầu Xanh",
                                390000,
                                460000,
                                "Cẩm tú cầu xanh Đà Lạt căng tròn phối cùng hoa cát tường trắng và giấy gói Hàn Quốc cao cấp, tạo cảm giác thanh lịch, nhẹ nhàng.",
                                "https://images.unsplash.com/photo-1526047932273-341f2a7631f9?auto=format&fit=crop&w=600&q=80"));
                products.put(6L, new Product(
                                6L,
                                "SP4106",
                                "Lẵng Hoa Khai Trương Hồng Phát",
                                750000,
                                880000,
                                "Lẵng hoa 2 tầng rực rỡ với đồng tiền, hồng môn, hướng dương và lan vũ nữ, gửi lời chúc khai trương hồng phát, vạn sự hanh thông.",
                                "https://images.unsplash.com/photo-1487530811176-3780de880c2d?auto=format&fit=crop&w=600&q=80"));
        }

        @Override
        public List<Product> findAll() {
                return new ArrayList<>(products.values());
        }

        @Override
        public Optional<Product> findById(Long id) {
                return Optional.ofNullable(products.get(id));
        }
}
