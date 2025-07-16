"use client";

import React, { useState } from "react";

type ProductInfoProps = {
    productList : string[];
    handleAddToCart : (product: string) => void
};

type CartItem = {
    name : String;
    quantity : number;
}

type PickedProductInfoProps = {
    cartItems: CartItem[];
};

type OrderFormProps = {
    totalPrice: number;
};

{/* 상품 목록 */}
function ProductInfo({ productList, handleAddToCart }: ProductInfoProps) {
    return (
    <section className="md:w-2/3 w-full">
                    <h2 className="text-2xl font-semibold mb-4">상품목록</h2>
                    <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
                        {productList.map((product, index) => (
                            <div key = {index} 
                            onClick={() => handleAddToCart(product)}
                            className="p-4 border rounded-lg shadow hover:shadow-md transition cursor-pointer hover:bg-gray-100">
                                {product}
                            </div>
                        ))}
                    </div>
                </section>
    )
}

{/* 선택된 상품 목록 */}
function PickedProductInfo({cartItems} : PickedProductInfoProps) {
    return (
    <div className="mb-4 space-y-2">
                        {cartItems.length > 0 ? (
                            cartItems.map((item, index) => (
                                <div key={index} className="border p-2 rounded bg-white">
                                    {item.name} x {item.quantity}
                                </div>
                            ))
                        ) : (
                            <p className="text-gray-500">선택된 상품이 없습니다.</p>
                        )}
                    </div>
    );
}

{/* 주문 폼 */}
function OrderForm({totalPrice} : OrderFormProps) {
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
                            <span className="text-blue-600">
                                ₩{totalPrice.toLocaleString()}
                            </span>
                        </div>

                        <button className="w-full bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-700 transition">
                            주문하기
                        </button>
                    </div>
    );
}


export default function Page() {
    const [cartItems, setCartItems] = useState<CartItem[]>([]);
    const productList = ["상품1", "상품2", "상품3"];
    const pricePerItem = 10000;

    // 클릭시, 어떻게 되는지
    const handleAddToCart = (product: string) => {
        setCartItems((prevItems) => {
            const existingItem = prevItems.find((item) => item.name === product);

            if (existingItem) {
                return prevItems.map((item) =>
                    item.name === product
                        ? { ...item, quantity: item.quantity + 1 }
                        : item
                );
            } else {
                return [...prevItems, { name: product, quantity: 1 }];
            }
        });
    };

    const calculateTotal = () => {
        return cartItems.reduce(
            (total, item) => total + item.quantity * pricePerItem,
            0
        );
    };

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
                    <PickedProductInfo cartItems={cartItems}/>

                    {/* 주문 폼 */}
                    <OrderForm totalPrice={calculateTotal()}/>
                </section>
            </div>
        </div>
    );
}