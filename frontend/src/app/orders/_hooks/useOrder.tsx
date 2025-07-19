"use client";
import { useEffect, useState } from "react";

export interface Product {
  id: number;
  productName: string;
  productDescription: string;
  productPrice: number;
  path: string;
}
export interface Orders {
  email: string;
  address: string;
  zipCode: string;
}
export interface CartItem {
  product: Product;
  quantity: number;
}
interface WishListItemFromServer {
  product: Product;
  quantity: number;
}
interface WishListFromServer {
  email: string;
  address: string;
  zipCode: string;
  wishListItems: WishListItemFromServer[];
}

const NEXT_PUBLIC_API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;

export function useOrder(wishListId: string | null) {
  const [orders, setOrders] = useState<Orders>({ email: "", address: "", zipCode: "" });
  const [cartItems, setCartItems] = useState<CartItem[]>([]);

  useEffect(() => {
    if (wishListId) {
      fetch(`${NEXT_PUBLIC_API_BASE_URL}/api/v1/wishlist?wishListId=${wishListId}`)
          .then(res => res.ok ? res.json() : null)
        .then((data: WishListFromServer | null) => {
          console.log("📥 응답 데이터:", data);
          if (data && data.email && data.wishListItems) {
            setOrders({
              email: data.email,
              address: data.address,
              zipCode: data.zipCode,
            });
            setCartItems(
              Array.isArray(data.wishListItems)
                ? data.wishListItems.map((item) => ({
                    product: item.product,
                    quantity: item.quantity,
                  }))
                : []
            );
          } else {
            // fallback
            const savedOrders = localStorage.getItem("ordersInfo");
            if (savedOrders) setOrders(JSON.parse(savedOrders));
            const savedCart = localStorage.getItem("cartItems");
            if (savedCart) setCartItems(JSON.parse(savedCart));
          }
        })
        .catch(() => {
          // fallback
          const savedOrders = localStorage.getItem("ordersInfo");
          if (savedOrders) setOrders(JSON.parse(savedOrders));
          const savedCart = localStorage.getItem("cartItems");
          if (savedCart) setCartItems(JSON.parse(savedCart));
        });
    } else {
      // fallback
      const savedOrders = localStorage.getItem("ordersInfo");
      if (savedOrders) setOrders(JSON.parse(savedOrders));
      const savedCart = localStorage.getItem("cartItems");
      if (savedCart) setCartItems(JSON.parse(savedCart));
    }
  }, [wishListId]);

  return { orders, cartItems };
}