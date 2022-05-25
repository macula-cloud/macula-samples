import { useState } from 'react';

export default function useForceUpdate() {
  const forceUpdate = useState(0)[1];
  return () => forceUpdate(x => x + 1);
}
