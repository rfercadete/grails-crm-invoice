/*
 * Copyright (c) 2012 Goran Ehrsson.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package grails.plugins.crm.invoice

import grails.plugins.crm.core.TenantUtils

/**
 *
 * @author Goran Ehrsson
 * @since 0.1
 */
class CrmInvoiceItem {


    public static final List<String> BIND_WHITELIST = ['orderIndex', 'productId', 'productName', 'comment',
                                                       'unit', 'quantity', 'price', 'vat'].asImmutable()

    Integer orderIndex
    String productId
    String productName
    String comment
    String unit
    Float quantity
    Float price
    Float vat

    static belongsTo = [invoice: CrmInvoice]

    static constraints = {
        orderIndex()
        productId(maxSize: 40, blank: false)
        productName(maxSize: 255, blank: false)
        comment(maxSize: 255, nullable: true)
        unit(maxSize: 40, nullable: false, blank: false)
        quantity(nullable: false, min: -999999f, max: 999999f, scale: 2)
        price(nullable: false, min: -999999f, max: 999999f, scale: 2)
        vat(nullable: false, min: 0f, max: 1f, scale: 2)
    }

    static transients = ['priceVAT', 'totalPrice', 'totalPriceVAT', 'totalVat', 'empty', 'dao']

    transient Double getPriceVAT() {
        def p = price
        if (!p) {
            return 0
        }
        def v = vat ?: 0
        return p + (p * v)
    }

    transient Double getTotalVat() {
        getTotalPrice() * (vat ?: 0)
    }

    transient Double getTotalPrice() {
        (quantity ?: 0) * (price ?: 0)
    }

    transient Double getTotalPriceVAT() {
        def p = getTotalPrice()
        def v = vat ?: 0
        return p + (p * v)
    }

    String toString() {
        "$quantity $unit $productId"
    }

    transient boolean isEmpty() {
        if(productId != null) return false
        if(productName != null) return false
        if(comment != null) return false
        if(quantity != null) return false
        if(price != null) return false

        return true
    }

    transient Map<String, Object> getDao() {
        [orderIndex: orderIndex, productId: productId, productName: productName, comment: comment,
                quantity: quantity, unit: unit, vat: vat,
                price: price, priceVAT: getPriceVAT(),
                totalPrice: getTotalPrice(), totalPriceVAT: getTotalPriceVAT()]
    }
}

