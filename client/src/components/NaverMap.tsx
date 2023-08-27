import React, { useEffect, useRef } from 'react';
import { NAVER_API_CLIENT_ID } from './../common/settings';
import { useAppDispatch, useAppSelector } from '../store';
import { mapLoad } from '../slices/mapSlice';


function NaverMap() {
  const mapLoaded = useNaverMap();
  const mapRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    if (mapLoaded) {
      if (mapRef.current !== null) {
        const map = new naver.maps.Map(mapRef.current, {});
  
        console.log(map);
      }
    }
  }, [mapLoaded]);

  return (
    <div id="map" ref={mapRef}></div>
  );
}

export default React.memo(NaverMap);

export function useNaverMap() {
  const dispatch = useAppDispatch();

  const loaded = useAppSelector((state) => state.map.loaded);

  useEffect(() => {
    if (!loaded) {
      const naverMapScript = document.createElement('script');
      naverMapScript.src = `https://oapi.map.naver.com/openapi/v3/maps.js?ncpClientId=${NAVER_API_CLIENT_ID}`;
      document.head.appendChild(naverMapScript);

      naverMapScript.addEventListener('load', () => {
        dispatch(mapLoad());
      });
    }
  })

  return loaded;
}
