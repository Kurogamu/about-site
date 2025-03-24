# Using a Slow Windows Tablet as a Workstation

## How we got here

For a long time I've enjoyed small computers and the idea of having a compact Linux gadget that's actually useful. I have had an Asus EEE PC (on which I learned to compile Linux and Python); a ClockworkPI DevTerm; and various tablets and smartphones.

In 2018 the Microsoft Surface Go seemed like a fairly good option: it was affordable, decent amount of RAM, pen support, expandable storage and ran on X86 while allowing for installing any OS. Unfortunately Linux for tablets was only barely usable back then and aside from being small it had few advantages over my laptop.

In 2024 my family and I moved to Canada which forced me to consider what devices were worthwhile to bring along. I opted for the Surface Go due to it being small and lightweight, expecting to replace it with a laptop as soon as I found employment.

## What it can do

Surprisingly, the device works really well for most development tasks. I have installed NixOS which only required minimal setup.

Vim naturally runs without any problems, as does a hot-swapping development server for building websites. I've used it for a while now to view large PDF documents (specifically TTRPG books). For most tasks it is responsive enough. I can even plug into an external screen like any laptop would. I haven't had trouble with any peripherals yet. Even the battery is at a decent 4-5 hours despite it being nearly 7 years old. It is fanless and therefore very quiet.

The advantage of its size (aside from portability) lies in how the restricted screen area encourages focused work. For many tasks (like editing and previewing LaTeX or CSS) it's fairly annoying, but writing and reading is quite pleasant. It allocates minimal desk space, which is especially nice when using a proper external keyboard.

The biggest surprise (apart from decent Linux support) is how the official keyboard is actually really nice. It easily rivals a 13" laptop and adds little volume and weight to the setup, especially as it behaves as a case.

## What it can't do

Multitasking is rather terrible. Opening LinkedIn (which I admittedly avoid) essentially causes most other programs to crash. Arguably this encourages more efficient code.

Booting with an encrypted disk without a physical keyboard is technically possible, but not straightforward in Linux.

Ironically it's really terrible to run Windows on it.

While the spec sheet for the integrated GPU claims 4K support at 60hz, I have tried multiple adapters and connections with no such luck. It is possible that the built in USB port simply doesn't support it.

## Where do we go from here

Small laptops have mostly been replaced by tablets and are fairly uncommon.

The 4th iteration of Surface Go uses a fairly slow processor and doesn't really seem worth its price. Might be a good choice for its battery life though.

It should be possible to use an Android tablet that supports installing package managers (for example using LineageOS and termux). While this would severely restrict options in terms of display managers, it would be a reasonable solution for a secondary computer.

The recently announced 12-inch framework laptop looks quite promising as well.
