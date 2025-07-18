"use client";

import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import { useSearchParams } from "next/navigation";

const NEXT_PUBLIC_API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;

type Product = {
  id: number;
  productName: string;
  productDescription: string;
  productPrice: number;
  path: string;
};

type CartItem = {
  product: Product;
  quantity: number;
};

export function useOrder() {
  const router = useRouter();
  const searchParams = useSearchParams();

  const [cartItems, setCartItems] = useState<CartItem[]>([]);
  const [productList, setProductList] = useState<Product[]>([]);
  const [email, setEmail] = useState("");
  const [address, setAddress] = useState("");
  const [zipCode, setZipCode] = useState("");
  const wishListId = searchParams.get("wishListId");

  // 클릭시, carItem에 아이템을 추가한다.
  const handleAddToCart = (product: Product) => {
    setCartItems((prevItems) => {
      const existingItem = prevItems.find(
        (item) => item.product.id === product.id
      );

      if (existingItem) {
        return prevItems.map((item) =>
          item.product.id === product.id
            ? { ...item, quantity: item.quantity + 1 }
            : item
        );
      } else {
        return [...prevItems, { product, quantity: 1 }];
      }
    });
  };

  // 클릭시, carItem에 아이템을 버린다.
  const handleRemoveFromCart = (productId: number) => {
    setCartItems(
      (prevItems) =>
        prevItems
          .map((item) =>
            item.product.id === productId
              ? { ...item, quantity: item.quantity - 1 }
              : item
          )
          .filter((item) => item.quantity > 0) // 수량이 0인 항목은 제거
    );
  };

  // 클릭시, 주문 정보를 서버로 전송
  const handleOrderSubmit = async () => {
    const totalQuantity = cartItems.reduce(
      (sum, item) => sum + item.quantity,
      0
    );
    const totalPrice = calculateTotal();

    const orderData = {
      email,
      address,
      zipCode,
      totalQuantity,
      totalPrice,
      items: cartItems.map((item) => ({
        id: item.product.id,
        quantity: item.quantity,
      })),
    };

    try {
      const res = await fetch(`${NEXT_PUBLIC_API_BASE_URL}/api/v1/wishlist/`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(orderData),
      });

      if (!res.ok) {
        throw new Error("서버 응답 실패");
      }

      const responseJson = await res.json();
      console.log("서버 응답:", responseJson);
      alert("주문이 완료되었습니다!");

      router.push(`/orders?wishListId=${responseJson}`);
    } catch (error) {
      console.error("주문 실패:", error);
      alert("주문 중 오류가 발생했습니다.");
    }
  };

  const calculateTotal = () => {
    return cartItems.reduce(
      (total, item) => total + item.quantity * item.product.productPrice,
      0
    );
  };

  //상품 목록 json 형태로 가져오는 코드
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const res = await fetch(`${NEXT_PUBLIC_API_BASE_URL}/api/v1/products`);
        if (!res.ok) throw new Error("데이터 요청 실패");
        const json = await res.json();
        setProductList(json);
      } catch (err) {
        console.error("API 요청 실패:", err);
      }
    };

    fetchProducts();
  }, []);

  // 위시리스트의 정보를 조회한 후 상품목록과 회원정보를 받는다.
  useEffect(() => {
    if (!wishListId) return;

    const fetchWishListData = async () => {
      try {
        const res = await fetch(
          `${NEXT_PUBLIC_API_BASE_URL}/api/v1/wishlist/${wishListId}`
        );

        if (!res.ok) {
          throw new Error("위시리스트 조회 실패");
        }

        const data = await res.json();

        setCartItems(data.items); // 서버에서 { product: {...}, quantity } 형식으로 줘야 함
        setEmail(data.email);
        setAddress(data.address);
        setZipCode(data.zipCode);
      } catch (error) {
        console.error("위시리스트 로딩 오류:", error);
      }
    };

    fetchWishListData();
  }, [wishListId]);

  return {
    productList,
    cartItems,
    email,
    address,
    zipCode,
    wishListId,
    setEmail,
    setAddress,
    setZipCode,
    handleAddToCart,
    handleRemoveFromCart,
    handleOrderSubmit,
    calculateTotal,
  };
}
