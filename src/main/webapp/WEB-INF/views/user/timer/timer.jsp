<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

@import "compass/css3";

.wrapper {
  position: relative;
  margin: 40px auto;
  background: white;
}

@mixin timer($item, $duration, $size, $color, $border, $hover: running) {
  #{$item}, #{$item} * { @include box-sizing(border-box); }

  #{$item} { 
    width: $size;
    height: $size;
  }

  #{$item} .pie {
    width: 50%;
    height: 100%;
    transform-origin: 100% 50%;
    position: absolute;
    background: $color;
    border: #{$border};
  }

  #{$item} .spinner {
    border-radius: 100% 0 0 100% / 50% 0 0 50%;
    z-index: 200;
    border-right: none;
    animation: rota $duration + s linear infinite;
  }

  #{$item}:hover .spinner,
  #{$item}:hover .filler, 
  #{$item}:hover .mask {
    animation-play-state: $hover;    
  }

  #{$item} .filler {
    border-radius: 0 100% 100% 0 / 0 50% 50% 0; 
    left: 50%;
    opacity: 0;
    z-index: 100;
    animation: opa $duration + s steps(1,end) infinite reverse;
    border-left: none;
  }

  #{$item} .mask {
    width: 50%;
    height: 100%;
    position: absolute;
    background: inherit;
    opacity: 1;
    z-index: 300;
    animation: opa $duration + s steps(1,end) infinite;
  }

  @keyframes rota {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
  }

  @keyframes opa {
    0% { opacity: 1; }
    50%, 100% { opacity: 0; }
  }
}

@include timer('.wrapper', 5, 250px, #08C, '5px solid rgba(0,0,0,0.5)');

<section class="contents">
	<div class="wrapper">
	  <div class="pie spinner"></div>
	  <div class="pie filler"></div>
	  <div class="mask"></div>
	</div>
</section>