package nhom7.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.servers.Server;
import nhom7.model.Cart;
import nhom7.model.CartItem;
import nhom7.model.Food;
import nhom7.model.User;
import nhom7.repository.CartItemRepository;
import nhom7.repository.CartRepository;
import nhom7.repository.FoodRepository;
import nhom7.request.AddCartItemRequest;

@Service	
public class CartServiceImpl implements CartService{

	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private FoodService foodService;
	
	@Override
	public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
		User user = userService.findUserByJwtToken(jwt);
		
		Food food = foodService.findFoodById(req.getFoodId());
		
		Cart cart = cartRepository.findByCustomerId(user.getId());
		
		for(CartItem cartItem : cart.getItems()) {
			if(cartItem.getFood().equals(food)) {
				int newQuantity = cartItem.getQuantity()+req.getQuantity();
				return updateCartItemQuantity(cartItem.getId(), newQuantity);
			}
		}
		
		CartItem newCartItem = new CartItem();
		newCartItem.setFood(food);
		newCartItem.setCart(cart);
		newCartItem.setQuantity(req.getQuantity());
		newCartItem.setIngredients(req.getIngredients());
		newCartItem.setTotalPrice(req.getQuantity()*food.getPrice());
		
		CartItem saveCartItem=cartItemRepository.save(newCartItem);
		cart.getItems().add(saveCartItem);
		return saveCartItem;
	}

	@Override
	public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
		Optional<CartItem> cartItemOptinal = cartItemRepository.findById(cartItemId);
		if(cartItemOptinal.isEmpty()) {
			throw new Exception("Không tìm thấy món ăn trong giỏ hàng");
		}
		
		CartItem item = cartItemOptinal.get();
		item.setQuantity(quantity);
		
		item.setTotalPrice(item.getFood().getPrice()*quantity);
		return cartItemRepository.save(item);
	}

	@Override
	public Cart removeItemFormCart(Long cartItemId, String jwt) throws Exception {
		
		User user = userService.findUserByJwtToken(jwt);
		
		Cart cart = cartRepository.findByCustomerId(user.getId());
		
		Optional<CartItem> cartItemOptinal = cartItemRepository.findById(cartItemId);
		if(cartItemOptinal.isEmpty()) {
			throw new Exception("Không tìm thấy món ăn trong giỏ hàng");
		}
		
		CartItem item = cartItemOptinal.get();
		
		cart.getItems().remove(item);
		return cartRepository.save(cart);
	}

	@Override
	public Long calculateCartTotals(Cart cart) throws Exception {
		Long total = 0L;
		
		for(CartItem cartItem : cart.getItems()) {
			total += cartItem.getFood().getPrice()*cartItem.getQuantity();
		}
		return total;
	}

	@Override
	public Cart findCartById(Long id) throws Exception {
		Optional<Cart> optionalCart=cartRepository.findById(id);
		if(optionalCart.isEmpty()) {
			throw new Exception("Không tìm thấy giỏ hàng với mã này");
		}
		return optionalCart.get();
	}

	@Override
	public Cart findCartByUserId(Long userId) throws Exception {
//		User user = userService.findUserByJwtToken(jwt);
		Cart cart = cartRepository.findByCustomerId(userId);
		cart.setTotal(calculateCartTotals(cart));
		return cart;
	}

	@Override
	public Cart clearCart(Long userId) throws Exception {
//		User user = userService.findUserByJwtToken(jwt);
		Cart cart = findCartByUserId(userId);
		
		cart.getItems().clear();
		return cartRepository.save(cart);
	}

}
