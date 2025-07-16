"use client";

import React, { useState, useEffect } from "react";

const NEXT_PUBLIC_API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;

type Product = {
  id: number;
  productName: string;
  productDescription: string;
  productPrice: number;
  path: string;
};

type ProductInfoProps = {
  productList: Product[];
  handleAddToCart: (product: Product) => void;
};

type CartItem = {
  product: Product;
  quantity: number;
};

type PickedProductInfoProps = {
  cartItems: CartItem[];
  handleRemoveFromCart: (productId: number) => void;
};

type OrderFormProps = {
  totalPrice: number;
};

{
  /* 상품 목록 */
}
function ProductInfo({ productList, handleAddToCart }: ProductInfoProps) {
  return (
    <section className="md:w-2/3 w-full">
      <h2 className="text-2xl font-semibold mb-4">상품목록</h2>
      <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
        {productList.map((product) => (
          <div
            key={product.id}
            onClick={() => handleAddToCart(product)}
            className="p-4 border rounded-lg shadow hover:shadow-md transition cursor-pointer hover:bg-gray-100 bg-white"
          >
            <img
              src={`${NEXT_PUBLIC_API_BASE_URL}/${product.path}.jpg`} // 👈 정적 파일 폴더에 맞게 확장자 포함
              alt={product.productName}
              className="w-full h-40 object-cover rounded mb-2"
            />
            <h3 className="text-lg font-semibold">{product.productName}</h3>

            <p className="text-sm text-gray-600 mb-1">
              {product.productDescription}
            </p>

            <p className="text-blue-600 font-semibold">
              ₩{product.productPrice.toLocaleString()}
            </p>
          </div>
        ))}
      </div>
    </section>
  );
}

{
  /* 선택된 상품 목록 */
}
function PickedProductInfo({
  cartItems,
  handleRemoveFromCart,
}: PickedProductInfoProps) {
  return (
    <div className="mb-4 space-y-2">
      {cartItems.length > 0 ? (
        cartItems.map((item, index) => (
          <div
            key={index}
            className="border p-2 rounded bg-white flex justify-between items-center"
          >
            <span>
              {item.product.productName} x {item.quantity}
            </span>
            <button
              onClick={() => handleRemoveFromCart(item.product.id)}
              className="text-red-500 border border-red-500 px-2 py-1 rounded hover:bg-red-100"
            >
              -
            </button>
          </div>
        ))
      ) : (
        <p className="text-gray-500">선택된 상품이 없습니다.</p>
      )}
    </div>
  );
}

{
  /* 주문 폼 */
}
function OrderForm({ totalPrice }: OrderFormProps) {
  return (
    <div className="space-y-4">
      <div>
        <label className="block mb-1 font-medium">이메일</label>
        <input
          type="email"
          className="w-full border-2 border-gray-300 rounded p-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
      </div>

      <div>
        <label className="block mb-1 font-medium">주소</label>
        <input
          type="text"
          className="w-full border-2 border-gray-300 rounded p-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
      </div>

      <div>
        <label className="block mb-1 font-medium">우편주소</label>
        <input
          type="text"
          className="w-full border-2 border-gray-300 rounded p-2 focus:outline-none focus:ring-2 focus:ring-blue-400"
        />
      </div>

      <div className="text-lg font-semibold">
        총금액:{" "}
        <span className="text-blue-600">₩{totalPrice.toLocaleString()}</span>
      </div>

      <button className="w-full bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-700 transition">
        주문하기
      </button>
    </div>
  );
}

export default function Page() {
  const [cartItems, setCartItems] = useState<CartItem[]>([]);
  const [productList, setProductList] = useState<Product[]>([]);

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

  const calculateTotal = () => {
    return cartItems.reduce(
      (total, item) => total + item.quantity * item.product.productPrice,
      0
    );
  };

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

  //🟡 상품 목록 json 형태로 가져오는 코드
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

  return (
    <div className="p-8 max-w-6xl mx-auto font-sans">
      <h1 className="text-4xl font-bold text-center mb-8">Grids & Circles</h1>

      <div className="flex flex-col md:flex-row gap-8">
        <ProductInfo
          productList={productList}
          handleAddToCart={handleAddToCart}
        />

        {/* 장바구니 / 주문 폼 */}
        <section className="md:w-1/3 w-full">
          <h2 className="text-2xl font-semibold mb-4">장바구니</h2>

          {/* 선택된 상품 목록 */}
          <PickedProductInfo
            cartItems={cartItems}
            handleRemoveFromCart={handleRemoveFromCart}
          />

          {/* 주문 폼 */}
          <OrderForm totalPrice={calculateTotal()} />
        </section>
      </div>
    </div>
  );
}
