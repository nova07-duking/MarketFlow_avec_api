---
name: Professional Commerce
colors:
  surface: '#f7f9ff'
  surface-dim: '#d7dae0'
  surface-bright: '#f7f9ff'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f1f4fa'
  surface-container: '#ebeef4'
  surface-container-high: '#e5e8ee'
  surface-container-highest: '#dfe3e8'
  on-surface: '#181c20'
  on-surface-variant: '#414754'
  inverse-surface: '#2d3135'
  inverse-on-surface: '#eef1f7'
  outline: '#727785'
  outline-variant: '#c1c6d6'
  surface-tint: '#005bc0'
  primary: '#005bbf'
  on-primary: '#ffffff'
  primary-container: '#1a73e8'
  on-primary-container: '#ffffff'
  inverse-primary: '#adc7ff'
  secondary: '#2b5bb5'
  on-secondary: '#ffffff'
  secondary-container: '#759efd'
  on-secondary-container: '#00337c'
  tertiary: '#9e4300'
  on-tertiary: '#ffffff'
  tertiary-container: '#c55500'
  on-tertiary-container: '#0e0200'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#d8e2ff'
  primary-fixed-dim: '#adc7ff'
  on-primary-fixed: '#001a41'
  on-primary-fixed-variant: '#004493'
  secondary-fixed: '#d9e2ff'
  secondary-fixed-dim: '#b0c6ff'
  on-secondary-fixed: '#001945'
  on-secondary-fixed-variant: '#00429c'
  tertiary-fixed: '#ffdbcb'
  tertiary-fixed-dim: '#ffb691'
  on-tertiary-fixed: '#341100'
  on-tertiary-fixed-variant: '#783100'
  background: '#f7f9ff'
  on-background: '#181c20'
  surface-variant: '#dfe3e8'
typography:
  h1:
    fontFamily: Inter
    fontSize: 40px
    fontWeight: '700'
    lineHeight: 48px
    letterSpacing: -0.02em
  h2:
    fontFamily: Inter
    fontSize: 32px
    fontWeight: '600'
    lineHeight: 40px
    letterSpacing: -0.01em
  h3:
    fontFamily: Inter
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
  body-lg:
    fontFamily: Inter
    fontSize: 18px
    fontWeight: '400'
    lineHeight: 28px
  body-md:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  body-sm:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
  label-md:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '500'
    lineHeight: 16px
    letterSpacing: 0.01em
  label-sm:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '600'
    lineHeight: 14px
  button:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '600'
    lineHeight: 16px
rounded:
  sm: 0.125rem
  DEFAULT: 0.25rem
  md: 0.375rem
  lg: 0.5rem
  xl: 0.75rem
  full: 9999px
spacing:
  base: 8px
  xs: 4px
  sm: 8px
  md: 16px
  lg: 24px
  xl: 32px
  xxl: 48px
  container-max: 1280px
  gutter: 24px
---

## Brand & Style

The brand personality of the design system is anchored in reliability, precision, and frictionless utility. It is designed to serve a dual-audience ecosystem: high-intent shoppers seeking a secure purchasing experience and professional sellers requiring a powerful, data-driven workspace. 

The design style follows a **Corporate / Modern** aesthetic. It prioritizes clarity over decoration, using ample whitespace and a disciplined grid to reduce cognitive load. The visual language evokes a sense of "quiet efficiency"—where the interface recedes to let product imagery and critical data take center stage. Trust is established through consistent alignment, high-legibility typography, and a restrained color palette that communicates stability.

## Colors

The color strategy for the design system centers on "Professional Blue" (#1A73E8) to signify action and security. This primary blue is used for core CTAs, progress indicators, and active states. 

- **Primary:** #1A73E8 is the primary engine for interaction.
- **Secondary:** A deeper navy (#0D47A1) is reserved for footer backgrounds and high-level seller navigation to provide visual weight.
- **Neutrals:** A scale of cool grays handles borders and secondary text. #F8F9FA is used for section backgrounds to subtly separate the seller dashboard modules from the canvas.
- **Semantic Colors:** Success (Green), Error (Red), and Warning (Amber) are used sparingly, following standard accessibility contrasts to ensure shipping alerts and payment statuses are unmistakable.

## Typography

The design system utilizes **Inter** for all text levels to leverage its exceptional readability and systematic feel. The type scale is built on a tight 4px baseline grid to ensure vertical rhythm across dense seller tables and product descriptions.

Headlines use a heavier weight and slight negative letter-spacing to appear more authoritative. Body text is optimized for long-form reading in product descriptions and shipping policies, maintaining a generous line height. Label styles are specifically tuned for form fields and data visualizations, ensuring that even at small sizes (12px), information remains legible for efficient processing.

## Layout & Spacing

The layout philosophy follows a **Fixed Grid** model for desktop viewports (centered at 1280px) and a fluid 4-column grid for mobile devices. A 12-column system is used for both the marketplace and the seller dashboard, though their applications differ:

- **Marketplace Layout:** Uses wide gutters (24px) to allow product cards to breathe and emphasizes vertical scrolling.
- **Seller Dashboard:** Uses a "Compressed View" with narrower margins and a fixed sidebar (280px). Content is organized into modular widgets that can span 3, 6, or 12 columns.
- **Spacing Rhythm:** All margins and paddings must be multiples of the 8px base unit. This ensures that form fields for shipping and OTP entry feel balanced and aligned with the surrounding content.

## Elevation & Depth

To maintain a professional and flat aesthetic, the design system uses **Tonal Layers** and **Low-Contrast Outlines** rather than heavy shadows.

- **Level 0 (Flat):** Used for the main canvas background (#FFFFFF).
- **Level 1 (Subtle):** Used for seller dashboard panels and shipping info cards. These use a 1px border (#E8EAED) or a very soft, diffused shadow (0px 2px 4px rgba(0,0,0,0.05)).
- **Level 2 (Active/Floating):** Reserved for dropdown menus, tooltips, and floating "Add to Cart" bars on mobile. These use a slightly more pronounced shadow (0px 8px 16px rgba(0,0,0,0.1)).
- **Interactive Depth:** On hover, product cards do not lift; instead, they transition to a slightly darker border color (#1A73E8) to indicate focus without breaking the layout's structural integrity.

## Shapes

The shape language is defined as **Soft (Level 1)**. This choice reinforces the "professional" and "systematic" nature of the design system.

- **Standard Components:** Buttons, input fields, and small cards use a 4px (0.25rem) corner radius. This provides a modern touch while maintaining a precise, engineering-led feel.
- **Large Containers:** Dashboard widgets and main content areas may use up to 8px (0.5rem) to distinguish them from smaller UI elements.
- **Selection Elements:** Checkboxes and radio buttons follow the standard 4px radius or full circles respectively, ensuring they remain familiar to the user.

## Components

The components within the design system are built for high-frequency interaction and clarity.

- **Form Fields:** Input fields feature clear top-aligned labels in `label-md`. The "OTP" component consists of six individual 48px x 56px boxes with centered, high-contrast type to ensure error-free entry. Shipping fields use standard widths (e.g., City and Zip on the same line) to optimize vertical space.
- **Buttons:** Primary buttons are solid #1A73E8 with white text. Secondary buttons use a 1px border in the primary color with a transparent background. Both use a 48px height for accessibility.
- **Cards:** Product cards are minimal, featuring a 1:1 aspect ratio for images, followed by `body-sm` for titles and bold `body-md` for pricing. No heavy shadows; use 1px borders.
- **Seller Dashboard Widgets:** These are structured modules with a header row (including a title and "View All" link) and a content area. Data tables within these widgets use zebra-striping with #F8F9FA for better row tracking.
- **Chips/Status Indicators:** Use light-tinted backgrounds (e.g., light blue for 'Processing', light green for 'Shipped') with darkened text for high contrast and quick status recognition.